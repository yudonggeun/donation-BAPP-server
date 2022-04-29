package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.*;

import java.util.List;

public interface TransactionService {
    //포인트 충전
    void pay(Member member, Long amount);
    //포인트 환불
    void payback(Member member, Long amount);
    //켐페인 거래 내역 조회
    List<TransactionDetailDto> getTransactionHistory(Long campaignId);
    //유저 거래 내역 조회
    List<TransactionDto> getTransactionHistory(Wallet wallet);
    //캠패인 자금 출금
    void withdraw(Campaign campaign, Member member, TransactionDetailDto dto);
    //기부하기
    void donate(Member member, Campaign campaign, Long amount);
}
