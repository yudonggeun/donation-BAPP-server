package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.Wallet;
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

    private final String BLOCK_CHAIN_URL = "https://api.baobab.klaytn.net:8651";
    private final String ABI_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.abi";
    private final String BYTECODE_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.bin";

    //SCO = SmartContractOwner
    private final String SCOAddress = "0x9e1063a35b302c6c3aa18cf9085de2f99bf1b6eb";
    private final String SCOPK = "0xfd1628a48611633f6c1c800414de0899b672a2586d4cc74f603a7a3a2fb30355";
    private final String contractAddress = "0x2979C52Ad2a065DC406779c020ebe9a6b548ac22";

    private final String ABI = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");

    private final Caver caver = new Caver(BLOCK_CHAIN_URL);

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

    @Override
    public String putTransaction(Wallet fromWallet, Transaction transaction, TransactionDetail detail) {
        String txHash = transfer(fromWallet.getPrivateKey(), transaction.getTo(), transaction.getAmount());
        if(detail != null)
            detail.setHashCode(txHash);
        return txHash;
    }

    @Override
    public Transaction getTransaction(String HashKey) {
        return null;
    }

    public String transferOwner(String toAddress, long amount){
        return transfer(SCOPK, toAddress, amount);
    }

    @Override
    public String transfer(String fromPk, String toAddress, long amount){
        //
        SingleKeyring executor = KeyringFactory.createFromPrivateKey(fromPk);
        if(caver.wallet.isExisted(executor.getAddress()))
            caver.wallet.updateKeyring(executor);
        else
            caver.wallet.add(executor);

        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            TransactionReceipt.TransactionReceiptData receipt = contract.getMethod("transfer").send(
                    Arrays.asList(
                            toAddress,
                            BigInteger.valueOf(amount)
                    ), sendOptions);

            log.info("txHash = {}", receipt.getSenderTxHash());
            log.info("transaction status {}", receipt.getStatus());

            return receipt.getSenderTxHash();
        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BlockChainException(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public BigInteger balanceOf(String accountPk) {
        SingleKeyring executor = KeyringFactory.createFromPrivateKey(accountPk);

        if(caver.wallet.isExisted(executor.getAddress()))
            caver.wallet.updateKeyring(executor);
        else
            caver.wallet.add(executor);

        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            ContractMethod method = contract.getMethod("balanceOf");

            List<Type> call = method.call(Arrays.asList(
                    executor.getAddress()
            ));

            return (BigInteger) call.get(0).getValue();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //handle exception..
            log.error(String.valueOf(e));
            throw e;
        }
    }
    public void supplyToken(long amount/*100만 단위*/, String msgSenderPk){

        SingleKeyring executor = KeyringFactory.createFromPrivateKey(msgSenderPk);

        if(caver.wallet.isExisted(executor.getAddress()))
            caver.wallet.updateKeyring(executor);
        else
            caver.wallet.add(executor);

        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            ContractMethod method = contract.getMethod("_supplyToken");

            TransactionReceipt.TransactionReceiptData receipt = method.send(
                    Arrays.asList(
                            BigInteger.valueOf(amount)
                    ), sendOptions);

            String status = receipt.getStatus();
            log.info("result = {}", status);
            log.info("class = {}", status.getClass());

        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("transaction execute fail : ", e);

//            return false;
        }
    }
    public boolean doseExecute(String status){
        return "0x1".equals(status);
    }
}
