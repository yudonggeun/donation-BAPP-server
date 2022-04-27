package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.Campaign;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 캠패인 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
 *
 */
@Data
public class CampaignSimpleDto {
    private Long campaignId;
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private String coverImagePath;
    private String description;
}
