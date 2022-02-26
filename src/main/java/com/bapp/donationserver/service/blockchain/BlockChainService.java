package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;

public interface BlockChainService {

    void putTransaction(Transaction transaction);

    Transaction getTransaction(String HashKey);
}
