package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.DonatedCampaign;
import com.bapp.donationserver.data.Member;

import java.util.List;

public interface DonatedCampaignRepository {
    void addDonatedCampaign(DonatedCampaign donatedCampaign);
    List<DonatedCampaign> getMyDonationList(Member member);
}
