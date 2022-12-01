package com.bapp.donationserver.repository;

import com.bapp.donationserver.entity.Campaign;
import com.bapp.donationserver.repository.custom.CustomCampaignRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long>, CustomCampaignRepository {
}
