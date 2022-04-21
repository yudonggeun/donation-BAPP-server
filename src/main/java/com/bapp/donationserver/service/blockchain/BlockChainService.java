package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.Wallet;

import java.math.BigInteger;

public interface BlockChainService {

    Wallet makeWallet();

    String putTransaction(Wallet fromWallet, Transaction transaction, TransactionDetail detail);

    Transaction getTransaction(String HashKey);

    String transfer(String fromPk, String toAddress, long amount);

    BigInteger balanceOf(String accountPk);
}
