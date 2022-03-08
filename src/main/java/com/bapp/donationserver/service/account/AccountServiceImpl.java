package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
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
    public Member getMemberInformation(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateMemberInformation(Member member, MemberDto updateMemberInformation) {
        member.setDto(updateMemberInformation);
//        memberRepository.update(email, updateMemberInformation);
    }

    @Override
    public List<CampaignSimpleDto> checkMyDonationList(Member member) {
        List<CampaignSimpleDto> donationList = new ArrayList<>();
        member.getDonatedCampaigns()
                .forEach(campaignInfo -> donationList.add(campaignInfo.getCampaign().getCampaignSimpleDto()));
        return donationList;
    }

    @Override
    @Transactional
    public void newMember(MemberDto data) {
        log.info("회원 가입 서비스 호출");
        //회원 생성
        Member newMember = new Member();
        newMember.setDto(data);
        //지갑 생성
        Wallet wallet = walletRepository.createWallet();
        newMember.setWallet(wallet);
        memberRepository.save(newMember);
    }

    @Override
    public Member login(String email, String password) throws Exception {
        Member member = memberRepository.findByEmail(email);
        if(member.getPassword().equals(password)){
            throw new Exception();
        }
        return member;
    }

    @Override
    @Transactional
    public void dropMember(Member member) {
        //db 에서 해당 멤버의 기록 삭제
        memberRepository.delete(member.getEmail());
        //지갑 삭제
        walletRepository.deleteWallet(member.getWallet());
    }
}
