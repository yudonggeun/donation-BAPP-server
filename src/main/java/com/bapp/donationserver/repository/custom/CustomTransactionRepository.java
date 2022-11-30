package com.bapp.donationserver.repository.custom;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;

public interface CustomTransactionRepository {
    void save(String fromPrivateKey, Transaction transaction, TransactionDetail detail);
}
