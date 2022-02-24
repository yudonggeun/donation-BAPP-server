package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Wallet;

public interface WalletRepository {

    Wallet createWallet();

    void deleteWallet(Wallet wallet);
}
