package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Campaign;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.repository.CampaignRepository;
import com.bapp.donationserver.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@Slf4j
@SpringBootTest
@TestInstance(PER_CLASS)
class JPACampaignRepositoryTest {

    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    WalletRepository walletRepository;

    Campaign campaign;
    String campaignName = "테스트 켐페인";
    String charityName = "기부단체 이름";
    LocalDate deadline = LocalDate.now();
    Long goalAmount = 123434L;
    String coverImagePath = "path:cover";
    String detailImagePath = "paht:detail";
    Boolean isAccepted = Boolean.TRUE;

    @BeforeAll
    void save() {
        //테스트 켐페인 db 저장
        campaign = new Campaign();
        campaign.setCampaignName(campaignName);
        campaign.setCharityName(charityName);
        campaign.setDeadline(deadline);
        campaign.setGoalAmount(goalAmount);
        campaign.setCoverImagePath(coverImagePath);
        campaign.setDetailImagePath(detailImagePath);
        campaign.setIsAccepted(isAccepted);
        List<String> categories = new ArrayList<>();
        categories.add("아동");
        categories.add("청년");

        //지갑 생성 및 등록
        Wallet wallet = walletRepository.createWallet();
        campaign.setWallet(wallet);

        campaignRepository.save(campaign, categories);
    }

    @Test
    void update() {
        //켐페인 필드 값 변경
        CampaignFullDto dto = sampleCampaignData();
        campaign.setCampaignFullDto(dto);
        campaignRepository.update(campaign, dto.getCategories());
        Campaign findCampaign = campaignRepository.findById(campaign.getId());

        assertThat(new CampaignFullDto(findCampaign)).isEqualTo(dto);
    }

    @Test
    void delete() {
        CampaignFullDto dto = sampleCampaignData();
        Campaign test = new Campaign();
        test.setCampaignFullDto(dto);
        campaignRepository.save(test, dto.getCategories());
        campaignRepository.delete(test);
    }

    @Test
    void findCampaignListByCondition() {
    }

    private CampaignFullDto sampleCampaignData() {
        Campaign campaign = new Campaign();
        campaign.setCampaignName("변경");
        campaign.setCharityName("변경단체");
        campaign.setDeadline(LocalDate.of(2000, 1, 1));
        campaign.setGoalAmount(0L);
        campaign.setCoverImagePath("change path by a");
        campaign.setDetailImagePath("detail Iamge path");

        return new CampaignFullDto(campaign);
    }

}