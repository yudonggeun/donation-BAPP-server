package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Wallet;

public interface WalletRepository {
    //지갑 생성
    Wallet createWallet();
    //지갑 삭제
    void deleteWallet(Wallet wallet);
    //지갑 조회
    Wallet getWallet(String walletId);
}
