package com.bapp.donationserver.blockchain.service;

public interface BlockChainService {

    public String sendMoney(String from, String to, long amount);
}
