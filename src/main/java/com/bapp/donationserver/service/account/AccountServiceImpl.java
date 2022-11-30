package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.exception.IllegalUserDataException;
import com.bapp.donationserver.repository.DonatedCampaignRepository;
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
    private final DonatedCampaignRepository donatedCampaignRepository;

    @Override
    public Member getMember(String email) {
        return memberRepository.findById(email).orElseThrow(() -> new IllegalUserDataException("멤버 조회 실패"));
    }

    @Override
    @Transactional
    public void updateMember(String email, MemberDto updateMemberInformation) {
        Member member = getMember(email);
        member.setDto(updateMemberInformation);
    }

    @Override
    public void addDonatedCampaign(String email, Campaign campaign) {
        Member member = getMember(email);
        DonatedCampaign info = new DonatedCampaign();
        info.setMember(member);
        info.setCampaign(campaign);
        donatedCampaignRepository.addDonatedCampaign(info);
    }

    @Override
    public List<CampaignSimpleDto> getDonationListByEmail(String email) {
        List<CampaignSimpleDto> donationList = new ArrayList<>();

        Member member = getMember(email);
        donatedCampaignRepository.getMyDonationList(member)
                .forEach(donate -> donationList.add(new CampaignFullDto(donate.getCampaign())));
        return donationList;
    }

    @Override
    @Transactional
    public boolean createNewMember(MemberDto data) {
        try {
            Wallet wallet = walletRepository.createWallet();
            Member newMember = new Member(data.getEmail(), wallet, data.getMemberType());
            newMember.setDto(data);
            memberRepository.save(newMember);
        } catch (Exception e){
            e.printStackTrace();
            log.info("[message:회원 정보 생성 실패], [회원 정보, " + data + "]");
            return false;
        }
        return true;
    }

    @Override
    public Member login(String email, String password) {
        Member member = getMember(email);
        if(!member.getPassword().equals(password)){
            throw new IllegalUserDataException("로그인에 실패했습니다.");
        }
        return member;
    }

    @Override
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
        walletRepository.deleteWallet(member.getWallet());
    }
}
