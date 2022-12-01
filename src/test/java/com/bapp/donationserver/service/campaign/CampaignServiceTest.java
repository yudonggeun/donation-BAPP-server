package com.bapp.donationserver.service.campaign;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Slf4j
@TestInstance(PER_CLASS)
class CampaignServiceTest {

    @Autowired CampaignService campaignService;

    CampaignFullDto dto;
    Long id;
}