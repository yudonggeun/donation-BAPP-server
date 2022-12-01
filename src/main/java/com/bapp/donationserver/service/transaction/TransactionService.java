package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.entity.Campaign;
import com.bapp.donationserver.data.dto.*;

import java.util.List;

public interface TransactionService {
    //포인트 충전
    void pay(String email, Long amount);
    //포인트 환불
    void payback(String email, Long amount);
    //켐페인 거래 내역 조회
    List<TransactionDetailDto> getHistories(Long campaignId);
    //유저 거래 내역 조회
    List<TransactionDto> getSimpleHistories(String wallet);
    //캠패인 자금 출금
    void withdraw(Campaign campaign, MemberDto member, TransactionDetailDto dto);
    //기부하기
    void donate(String email, Campaign campaign, Long amount);
}
