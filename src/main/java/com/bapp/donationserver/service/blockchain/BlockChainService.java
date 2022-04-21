package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.Wallet;

import java.math.BigInteger;

public interface BlockChainService {

    Wallet makeWallet();

    String transfer(String fromPk, String toAddress, long amount);

    BigInteger balanceOf(String accountPk);

    boolean supplyToken(long amount/*100만 단위*/, String msgSenderPk);
}
