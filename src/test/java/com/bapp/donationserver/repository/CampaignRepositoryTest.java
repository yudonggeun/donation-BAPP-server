package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class CampaignRepositoryTest {

    @Autowired
    CampaignRepository campaignRepository;

    Campaign campaign;

    @BeforeEach
    void init() {
        campaign = new Campaign();
        campaign.setCampaignName("켐패인 이름");
//        campaign.setCurrentAmount(10000L);
        campaign.setIsAccepted(true);
        campaign.setCharityName("단체 이름");
        campaign.setDeadline(LocalDate.parse("2020-01-01"));
        campaign.setCoverImagePath("cover image");
        campaign.setDetailImagePath("detail image");
        campaign.setGoalAmount(100000L);

        log.info("테스트 켐패인 저장={}", campaign);
        campaignRepository.save(campaign);
    }

    @Test
    void updateSuccess() {
        //update campaign entity
        campaign.setCampaignName("수정된 켐패인 이름");

        Campaign findCampaign = campaignRepository.findById(campaign.getCampaignId());

        //검증
        assertThat(findCampaign).isEqualTo(campaign);
    }

    @Test
    void delete() {
        campaignRepository.delete(campaign.getCampaignId());
        Campaign target = campaignRepository.findById(campaign.getCampaignId());
        assertThat(target).isNull();
    }

    @Test
    void findCampaignListByCondition() {
    }
}