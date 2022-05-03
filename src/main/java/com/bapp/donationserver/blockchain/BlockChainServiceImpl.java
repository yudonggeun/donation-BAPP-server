package com.bapp.donationserver.blockchain;

import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.consts.BlockChainConst;
import com.bapp.donationserver.exception.BlockChainException;
import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.ContractMethod;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.keyring.KeyringFactory;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class BlockChainServiceImpl implements BlockChainService {

    private final String ABI = StringUtils.join(Files.readAllLines(Path.of(BlockChainConst.ABI_PATH)), "\n");

    private final Caver caver = new Caver(BlockChainConst.BLOCK_CHAIN_URL);

    public BlockChainServiceImpl() throws IOException {

    }

    @Override
    public Wallet makeWallet() {

        SingleKeyring generate = caver.wallet.keyring.generate();

        String privateKey = generate.getKey().getPrivateKey();
        String address = generate.getAddress();

        Wallet wallet = new Wallet();
        wallet.setAmount(0L);
        wallet.setId(address);
        wallet.setPrivateKey(privateKey);

        log.info("generate wallet : [address] {}, [private key] {}", address, privateKey);
        return wallet;
    }

    public String transferOwner(String toAddress, long amount){
        return transfer(BlockChainConst.OWNER_PRIVATE_KEY, toAddress, amount);
    }

    @Override
    public String transfer(String fromPk, String toAddress, long amount){

        SingleKeyring executor = KeyringFactory.createFromPrivateKey(fromPk);
        addKeyringAtCaver(executor);

        try {
            Contract contract = new Contract(caver, ABI, BlockChainConst.CONTRACT_ADDRESS);

            SendOptions sendOptions = getSendOptions(executor);
            TransactionReceipt.TransactionReceiptData receipt = contract.getMethod("transfer").send(
                    Arrays.asList(
                            toAddress,
                            BigInteger.valueOf(amount)
                    ), sendOptions);

            log.info("transaction hash = {}", receipt.getTransactionHash());
            log.info("transaction status {}", receipt.getStatus());

            return receipt.getTransactionHash();
        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BlockChainException(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public BigInteger balanceOf(String accountPk) {
        SingleKeyring executor = KeyringFactory.createFromPrivateKey(accountPk);

        addKeyringAtCaver(executor);

        try {
            Contract contract = new Contract(caver, ABI, BlockChainConst.CONTRACT_ADDRESS);

            ContractMethod method = contract.getMethod("balanceOf");

            List<Type> call = method.call(List.of(executor.getAddress()));

            return (BigInteger) call.get(0).getValue();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //handle exception..
            log.error(String.valueOf(e));
            throw e;
        }
    }

    @Override
    public boolean supplyToken(long amount/*100만 단위*/, String msgSenderPk){

        SingleKeyring executor = KeyringFactory.createFromPrivateKey(msgSenderPk);

        addKeyringAtCaver(executor);

        try {
            Contract contract = new Contract(caver, ABI, BlockChainConst.CONTRACT_ADDRESS);

            SendOptions sendOptions = getSendOptions(executor);
            ContractMethod method = contract.getMethod("_supplyToken");

            TransactionReceipt.TransactionReceiptData receipt = method.send(
                    List.of(
                            BigInteger.valueOf(amount)
                    ), sendOptions);

            String status = receipt.getStatus();
            log.info("supplyToken supply : {}", amount * 1_000_000);
            return doseExecute(status);

        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("transaction execute fail : ", e);
            return false;
        }
    }

    @NotNull
    private SendOptions getSendOptions(SingleKeyring executor) {
        SendOptions sendOptions = new SendOptions();
        sendOptions.setFrom(executor.getAddress());
        sendOptions.setGas(BigInteger.valueOf(400000));
        sendOptions.setFeeDelegation(true);
        sendOptions.setFeePayer(BlockChainConst.OWNER_ADDRESS);
        return sendOptions;
    }

    private void addKeyringAtCaver(SingleKeyring executor) {
        if (caver.wallet.isExisted(executor.getAddress()))
            caver.wallet.updateKeyring(executor);
        else
            caver.wallet.add(executor);
    }

    public boolean doseExecute(String status){
        return "0x1".equals(status);
    }
}
