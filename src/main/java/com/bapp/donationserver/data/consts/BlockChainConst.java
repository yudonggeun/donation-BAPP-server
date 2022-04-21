package com.bapp.donationserver.data.consts;

public interface BlockChainConst {
    String OWNER_ADDRESS = "0xe7b8d6c2b8ac13491fc05dd4e010fc6774f80818";
    String OWNER_PRIVATE_KEY = "0x20b6e93a5b56fc75465b154450e5b2ad47448b6eebcb747acf8bbb4b086c69ab";

    String CONTRACT_ADDRESS = "0x2979C52Ad2a065DC406779c020ebe9a6b548ac22";
    String ABI_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.abi";
    String BYTECODE_PATH = "src/main/java/com/bapp/donationserver/service/blockchain/solidity/contract.bin";

    String BAOBAB_URL = "https://api.baobab.klaytn.net:8651";
}
