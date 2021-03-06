package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.exception.IllegalUserDataException;
import com.bapp.donationserver.repository.MemberRepository;
import com.bapp.donationserver.repository.WalletRepository;
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
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final WalletRepository walletRepository;

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateMember(Member member, MemberDto updateMemberInformation) {
        member.setDto(updateMemberInformation);
        memberRepository.update(member);
    }

    @Override
    public void addDonatedCampaign(Member member, Campaign campaign) {
        DonatedCampaign info = new DonatedCampaign();
        info.setMember(member);
        info.setCampaign(campaign);
        memberRepository.addDonatedCampaign(info);
    }

    @Override
    public List<CampaignSimpleDto> checkMyDonationList(Member member) {
        List<CampaignSimpleDto> donationList = new ArrayList<>();

        memberRepository.getMyDonationList(member)
                .forEach(donatedCampaign -> {
                    log.info("기부 켐페인 : {}", donatedCampaign.getId());
                    donationList.add(donatedCampaign.getCampaign().getCampaignSimpleDto());
                });
        return donationList;
    }

    @Override
    @Transactional
    public void newMember(MemberDto data) {
        //지갑 생성
        Wallet wallet = walletRepository.createWallet();

        //회원 생성
        Member newMember = new Member(data.getEmail(), wallet, data.getMemberType());
        newMember.setDto(data);
        memberRepository.save(newMember);
    }

    @Override
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if(!member.getPassword().equals(password)){
            throw new IllegalUserDataException("로그인에 실패했습니다.");
        }
        return member;
    }

    @Override
    @Transactional
    public void dropMember(Member member) {

        //db 에서 해당 멤버의 기록 삭제
        memberRepository.delete(member);
        //지갑 삭제
        log.info("지갑 삭제");
        walletRepository.deleteWallet(member.getWallet());
    }
}
