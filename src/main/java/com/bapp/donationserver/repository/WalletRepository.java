package com.bapp.donationserver.repository;

import com.bapp.donationserver.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
