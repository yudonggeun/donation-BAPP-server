package com.bapp.donationserver.repository;

import com.bapp.donationserver.entity.DonatedCampaign;
import com.bapp.donationserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonatedCampaignRepository extends JpaRepository<DonatedCampaign, Long> {
    @Query("select d from DonatedCampaign d join fetch d.campaign where d.member = :member_email")
    List<DonatedCampaign> getMyDonationList(@Param("member_email") Member member);
}
