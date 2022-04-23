package com.bapp.donationserver.blockchain;

import com.bapp.donationserver.data.Wallet;

import java.math.BigInteger;

public interface BlockChainService {

    Wallet makeWallet();

    String transfer(String fromPk, String toAddress, long amount);

    BigInteger balanceOf(String accountPk);

    boolean supplyToken(long amount/*100만 단위*/, String msgSenderPk);
}
