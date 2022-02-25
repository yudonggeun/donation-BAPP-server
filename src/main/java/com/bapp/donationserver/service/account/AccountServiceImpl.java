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
    public MemberDto getMemberInformation(String email) {
        return memberRepository.findByEmail(email).getDto();
    }

    @Override
    @Transactional
    public void updateMemberInformation(String email, MemberDto updateMemberInformation) {
        memberRepository.update(email, updateMemberInformation);
    }

    @Override
    public List<CampaignSimpleDto> checkMyDonationList(String email) {
        List<CampaignSimpleDto> donationList = new ArrayList<>();
        memberRepository.findByEmail(email)
                .getDonatedCampaigns()
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
    public MemberDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email);

        return member != null && member.getPassword().equals(password)
                ? member.getDto() : null;
    }

    @Override
    @Transactional
    public void dropMember(String email) {
        Member member = memberRepository.findByEmail(email);
        //db 에서 해당 멤버의 기록 삭제
        memberRepository.delete(email);
        //지갑 삭제
        walletRepository.deleteWallet(member.getWallet());
    }
}
