package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlockChainServiceImpl implements BlockChainService{

    private String address;
    private String port;
    private Object blockChain;

    public BlockChainServiceImpl(){
        //초기화
    }

    @Override
    public String putTransaction(Transaction transaction, TransactionDetail detail) {
        return UUID.randomUUID().toString();
    }

    @Override
    public Transaction getTransaction(String HashKey) {
        return null;
    }
}
