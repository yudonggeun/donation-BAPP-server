package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.consts.BlockChainConst;
import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.*;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.keyring.KeyringFactory;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Slf4j
class BlockChainServiceImplTest {

    String BAOBAB_URL = "https://api.baobab.klaytn.net:8651";
    String depoly_pk = "0xfd1628a48611633f6c1c800414de0899b672a2586d4cc74f603a7a3a2fb30355";
    String DEPOLY_ADDRESS = "0x9e1063a35b302c6c3aa18cf9085de2f99bf1b6eb";
    String OTHER_ADDRESS = "0x97a2c0bc4bf464448d253561e33e2f658e21e6f4";
    String ohter_pk = "0x4e28b1525dc387d5a9712e49c42efd445b6381bcffbe8d8673baeefcab0331cf";

    //contract variable
    String contractAddress = "0x2979C52Ad2a065DC406779c020ebe9a6b548ac22";
    String ABI_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.abi";
    String BYTECODE_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.bin";

    String ABI = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");

    Caver caver = new Caver(BAOBAB_URL);

    BlockChainServiceImplTest() throws IOException {
    }

    @Test
    public void generateAccount() {

        SingleKeyring generate = caver.wallet.keyring.generate();

        String privateKey = generate.getKey().getPrivateKey();
        String address = generate.getAddress();

        System.out.println("privateKey = " + privateKey);
        System.out.println("address = " + address);

    }

    @Test
    public void execute(){

        SingleKeyring executor = KeyringFactory.createFromPrivateKey(depoly_pk);
        caver.wallet.add(executor);
        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            ContractMethod method = contract.getMethod("balanceOf");

            method.call(List.of(
                    DEPOLY_ADDRESS
            )).forEach(type -> {
                log.info("type : {}", type.getValue());
                log.info("type : {}", type.getTypeAsString());
            });

            List<ContractIOType> outputs = method.getOutputs();
            for (ContractIOType output : outputs) {
                log.info("output.getName() : {}", output.getName());
                log.info("output.getType : {}", output.getType());
            }

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //handle exception..
        }
    }

    @Test
    public void sampleTransfer(){
//        transfer(depoly_pk, OTHER_ADDRESS, 0);
        transfer(depoly_pk, "0x97a2c0bc4bf464448d253561e33e2f658e21e6f4", 200);

        transfer("0x4e28b1525dc387d5a9712e49c42efd445b6381bcffbe8d8673baeefcab0331cf", "0x19f6b14f590bb3103e87efbd9ea1c51a1c392789", 100);
    }

    public boolean transfer(String fromPk, String toAddress, long amount){
        //
        BigInteger value = BigInteger.valueOf(amount);
        SingleKeyring executor = KeyringFactory.createFromPrivateKey(fromPk);

        if (!caver.wallet.isExisted(executor.getAddress()))
            caver.wallet.add(executor);

        SingleKeyring owner = KeyringFactory.createFromPrivateKey(BlockChainConst.OWNER_PRIVATE_KEY);
        if(!caver.wallet.isExisted(BlockChainConst.OWNER_ADDRESS))
            caver.wallet.add(owner);

        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            sendOptions.setFeeDelegation(true);
            sendOptions.setFeePayer(BlockChainConst.OWNER_ADDRESS);
            log.info("sendOptions = {}", sendOptions.getFeeDelegation());
            ContractMethod method = contract.getMethod("transfer");

            TransactionReceipt.TransactionReceiptData receipt = method.send(
                    Arrays.asList(
                            toAddress,
                            value
                    ), sendOptions);

            return true;

        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("transaction execute fail : ", e);

            return false;
        }
    }

    @Test
    public void balanceOf(){
        String accountPk = depoly_pk;

        SingleKeyring executor = KeyringFactory.createFromPrivateKey(accountPk);
        caver.wallet.add(executor);
        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));

            ContractMethod method = contract.getMethod("balanceOf");

            method.call(List.of(
                    executor.getAddress()
            )).forEach(type -> {
                log.info("return = {}", type.getValue());
                log.info("type = {}", type.getValue().getClass());
            });

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //handle exception..
        }

    }

    @Test
    public void increaseSupply(){

        long amount = 1;
        String fromPk = ohter_pk;
        //
        BigInteger value = BigInteger.valueOf(amount);
        SingleKeyring executor = KeyringFactory.createFromPrivateKey(fromPk);
        caver.wallet.add(executor);

        try {
            Contract contract = new Contract(caver, ABI, contractAddress);

            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(executor.getAddress());
            sendOptions.setGas(BigInteger.valueOf(400000));
            ContractMethod method = contract.getMethod("_supplyToken");

            TransactionReceipt.TransactionReceiptData receipt = method.send(
                    List.of(
                            value
                    ), sendOptions);

            String status = receipt.getStatus();
            log.info("result = {}", status);
            log.info("class = {}", status.getClass());

        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("transaction execute fail : ", e);

//            return false;
        }
    }

    @Test
    public void sampleSelectTransaction() {

    }

    @Test
    public void selectTransaction(){
        String txHash = "0xd145e332d35c8adbf3bd0f358b037048c2d0f45857e345b27d60f2d49ba155d5";

        try {
            TransactionReceipt receipt = caver.rpc.klay.getTransactionReceipt(txHash).send();

            log.info("log");
            log.info("raw data = {}", receipt.getRawResponse());

            TransactionReceipt.TransactionReceiptData receiptData = receipt.getResult();

            log.info(receipt.getJsonrpc());
            log.info(receiptData.getSenderTxHash());
            log.info(receiptData.getContractAddress());

        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
            log.info("error");
        }
    }
    @Test
    public void showMethodInContract() {
        try {

            String ABI = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");

            Contract contract = new Contract(caver, ABI);

            contract.getMethods().forEach((methodName, contractMethod) -> System.out.println("methodName : " + methodName + ", ContractMethod : " + contractMethod));
            System.out.println("ContractAddress : " + contract.getContractAddress());
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }

    @Test
    public void load() {

        try {
            String ABI = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");

            Contract contract = new Contract(caver, ABI, contractAddress);
            contract.getMethods().forEach((methodName, contractMethod) -> System.out.println("methodName : " + methodName + ", ContractMethod : " + contractMethod));
            System.out.println("ContractAddress : " + contract.getContractAddress());
        } catch (IOException e) {
            //handle exception..
        }
    }

    @Test
    public void noGasTransaction(){

    }

/*    @Test
    public void deploy() {
        Caver caver = new Caver(BAOBAB_URL);
        SingleKeyring deployer = KeyringFactory.createFromPrivateKey(depoly_pk);
        caver.wallet.add(deployer);
        try {
            String ABI = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");
            String byteCode = StringUtils.join(Files.readAllLines(Path.of(ABI_PATH)), "\n");

            Contract contract = new Contract(caver, ABI);
            ContractDeployParams params = new ContractDeployParams(byteCode, (Object) null);
            List<Object> paramList = new ArrayList<>();
            paramList.add("DTToken");
            paramList.add("DTT");
            paramList.add(100);
            params.setDeployParams(paramList);
            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(deployer.getAddress());
            sendOptions.setGas(BigInteger.valueOf(30_0000));

            Contract newContract = contract.deploy(params, sendOptions);
            System.out.println("Contract address : " + newContract.getContractAddress());
        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //handle exception..
        }
    }*/
}