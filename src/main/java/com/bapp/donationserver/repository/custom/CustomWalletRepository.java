package com.bapp.donationserver.repository.custom;

import com.bapp.donationserver.data.Wallet;

public interface CustomWalletRepository {
    //지갑 생성
    Wallet createWallet();
    //지갑 업데이트
    void update(Wallet wallet);
}
