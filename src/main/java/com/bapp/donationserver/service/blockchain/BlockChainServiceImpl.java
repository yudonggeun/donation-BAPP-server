package com.bapp.donationserver.service.blockchain;

import com.bapp.donationserver.data.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BlockChainServiceImpl implements BlockChainService{

    private String address;
    private String port;
    private Object blockChain;

    public BlockChainServiceImpl(){
        //초기화
    }

    @Override
    public void putTransaction(Transaction transaction) {

    }

    @Override
    public Transaction getTransaction(String HashKey) {
        return null;
    }
}
