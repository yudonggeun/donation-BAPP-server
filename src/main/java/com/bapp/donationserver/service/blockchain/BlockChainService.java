package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;

public interface BlockChainService {

    String putTransaction(Transaction transaction, TransactionDetail detail);

    Transaction getTransaction(String HashKey);
}
