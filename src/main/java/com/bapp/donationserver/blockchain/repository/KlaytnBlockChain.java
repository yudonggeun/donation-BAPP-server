package com.bapp.donationserver.blockchain.repository;

import com.bapp.donationserver.entity.Wallet;

import java.math.BigInteger;

public interface KlaytnBlockChain {

    Wallet createBlockchainWallet();

    String transfer(String fromPk, String toAddress, long amount);

    BigInteger balanceOf(String accountPk);

    boolean supplyToken(long amount/*100만 단위*/, String msgSenderPk);
}
