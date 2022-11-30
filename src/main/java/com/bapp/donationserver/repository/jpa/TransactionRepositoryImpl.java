package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.TransactionDetail;
import com.bapp.donationserver.data.consts.BlockChainConst;
import com.bapp.donationserver.exception.BlockChainException;
import com.bapp.donationserver.repository.TransactionRepository;
import com.bapp.donationserver.blockchain.BlockChainService;
import com.bapp.donationserver.repository.custom.CustomTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Repository
@Slf4j
@Primary
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements CustomTransactionRepository {

    @PersistenceContext
    private final EntityManager em;
    private final BlockChainService blockChainService;

    @Override
    public void save(String  fromPrivateKey, Transaction transaction, TransactionDetail detail) {
        BigInteger balance = blockChainService.balanceOf(fromPrivateKey);
        if (balance.longValue() < transaction.getAmount()) {
            if (!(fromPrivateKey.equals(BlockChainConst.OWNER_PRIVATE_KEY) && blockChainService.supplyToken(100, BlockChainConst.OWNER_PRIVATE_KEY))) {
                throw new BlockChainException("klay network error : balance is insufficient");
            }
        }

        String id = blockChainService.transfer(fromPrivateKey, transaction.getTo(), transaction.getAmount());
        transaction.setId(id);
        em.persist(transaction);
        if(detail != null) {
            detail.setHashCode(id);
            em.persist(detail);
        }
    }
}
