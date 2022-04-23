package com.bapp.donationserver.data.consts;

public interface BlockChainConst {
    String OWNER_ADDRESS = "0x9e1063a35b302c6c3aa18cf9085de2f99bf1b6eb";
    String OWNER_PRIVATE_KEY = "0xfd1628a48611633f6c1c800414de0899b672a2586d4cc74f603a7a3a2fb30355";

    String CONTRACT_ADDRESS = "0x2979C52Ad2a065DC406779c020ebe9a6b548ac22";
    String ABI_PATH = "src/main/java/com/bapp/donationserver/blockchain/solidity/contract.abi";
    String BYTECODE_PATH = "src/main/java/com/bapp/donationserver/blockchain/solidity/contract.bin";

    String BLOCK_CHAIN_URL = "https://api.baobab.klaytn.net:8651";
}
