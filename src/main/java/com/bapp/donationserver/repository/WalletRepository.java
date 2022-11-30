package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.repository.custom.CustomWalletRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String>, CustomWalletRepository {
}
