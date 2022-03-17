package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class JPAWalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @Test
    void createWalletAndDeleteWallet() {
        Wallet wallet = walletRepository.createWallet();
        String wallet_id = wallet.getId();
        walletRepository.deleteWallet(wallet);
        Wallet findWallet = walletRepository.getWallet(wallet_id);

        assertThat(findWallet).isNull();
    }

    @Test
    void update() {
        Wallet wallet = walletRepository.createWallet();
        wallet.setAmount(wallet.getAmount() + 112345);
        walletRepository.update(wallet);
        Long amount = wallet.getAmount();
        Wallet findWallet = walletRepository.getWallet(wallet.getId());

        assertThat(amount).isEqualTo(findWallet.getAmount());
    }
}