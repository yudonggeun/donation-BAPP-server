package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.repository.WalletRepository;
import com.bapp.donationserver.service.blockchain.BlockChainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Slf4j
@Primary
@RequiredArgsConstructor
public class JPAWalletRepository implements WalletRepository {

    @PersistenceContext
    private final EntityManager em;
    private final BlockChainService blockChainService;

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        em.persist(wallet);
        return wallet;
    }

    @Override
    public void deleteWallet(Wallet wallet) {
        em.remove(wallet);
    }

    @Override
    public Wallet getWallet(String walletId) {
        return em.find(Wallet.class, walletId);
    }
}
