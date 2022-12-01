package com.bapp.donationserver.blockchain.service;

import com.bapp.donationserver.blockchain.repository.KlaytnBlockChain;
import com.bapp.donationserver.data.consts.BlockChainConst;
import com.bapp.donationserver.exception.BlockChainException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
public class BlockChainServiceImpl implements BlockChainService {

    private final KlaytnBlockChain klaytnBlockChain;

    @Override
    public String sendMoney(String from, String to, long amount) {
        BigInteger balance = klaytnBlockChain.balanceOf(from);
        if (!from.equals(BlockChainConst.OWNER_PRIVATE_KEY) ||
                !klaytnBlockChain.supplyToken(100, BlockChainConst.OWNER_PRIVATE_KEY) &&
                        balance.longValue() < amount) {
            throw new BlockChainException("klay network error : balance is insufficient");
        }

        return klaytnBlockChain.transfer(from, to, amount);
    }
}
