package com.bapp.donationserver.service.user;

import com.bapp.donationserver.data.*;
import com.bapp.donationserver.data.dto.*;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.DonationTransactionRepository;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NormalUserServiceImpl implements NormalUserService {

    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;
    private final DonationTransactionRepository transactionRepository;

    @Override
    @Transactional
    public void newMember(MemberDto data) {
        log.info("회원 가입 서비스 호출");
        //회원 생성
        Member newMember = new Member();
        newMember.setDto(data);
        //지갑 생성
        memberRepository.save(newMember);
    }

    @Override
    public MemberDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email);

        return member != null && member.getPassword().equals(password)
                ? member.getDto() : null;
    }

    @Override
    public List<CampaignSimpleDto> checkCampaignList(CampaignSearchConditionDto dto, MemberType memberType) {
        //조건 설정
        CampaignSearchCondition condition = new CampaignSearchCondition();
        condition.setDto(dto);
        condition.setMemberType(memberType);

        //db 조회 및 dto 변환
        List<CampaignSimpleDto> dtoList = new ArrayList<>();
        campaignRepository.findCampaignListByCondition(condition)
                .forEach(campaign -> dtoList.add(campaign.getCampaignSimpleDto()));

        log.info("정상 반환 ={}", dtoList);
        return dtoList;
    }

    @Override
    public CampaignFullDto checkDetailsOfCampaign(String campaignId) {
        return campaignRepository.findById(campaignId).getCampaignFullDto();
    }

    @Override
    @Transactional
    public void pay(String memberEmail, Long amount) {
        log.info("결제 완료");
        Member member = memberRepository.findByEmail(memberEmail);

        Wallet wallet = member.getWallet();
        wallet.setAmount(wallet.getAmount() + amount);
    }

    @Override
    public List<TransactionDto> checkDonationHistory(String campaignId) {
        List<TransactionDto> dtoList = new ArrayList<>();
        transactionRepository.findByCampaignId(campaignId).forEach(transaction -> dtoList.add(transaction.getDto()));
        return dtoList;
    }
}
