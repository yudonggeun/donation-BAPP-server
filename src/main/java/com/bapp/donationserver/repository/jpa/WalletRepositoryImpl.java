package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.blockchain.BlockChainService;
import com.bapp.donationserver.repository.custom.CustomWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WalletRepositoryImpl implements CustomWalletRepository {

    @PersistenceContext
    private final EntityManager em;
    private final BlockChainService blockChainService;

    @Override
    public Wallet createWallet() {
        Wallet wallet = blockChainService.makeWallet();
        em.persist(wallet);
        return wallet;
    }

    @Override
    public void update(Wallet wallet) {
        em.merge(wallet);
    }
}
