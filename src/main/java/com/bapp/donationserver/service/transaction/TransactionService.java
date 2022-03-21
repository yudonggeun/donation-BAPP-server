package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.*;

import java.util.List;

public interface TransactionService {
    //포인트 충전
    void pay(Member member, Long amount);
    //기부 사용 내역 조회
    List<TransactionDto> getDonationHistory(Long campaignId);
    //캠패인 자금 출금
    void withdraw(Campaign campaign, Member member, TransactionDto dto);
    //기부하기
    void donate(Member member, Campaign campaign, Long amount);
}
