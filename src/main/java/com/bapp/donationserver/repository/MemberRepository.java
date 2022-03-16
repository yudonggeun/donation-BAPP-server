package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.MemberDto;

import java.util.List;

public interface MemberRepository {
    void save(Member member);
    void update(Member member);
    void delete(Member member);
    Member findByEmail(String email);
    List<DonatedCampaign> getMyDonationList(Member member);
}
