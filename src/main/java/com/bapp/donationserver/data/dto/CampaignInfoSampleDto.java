package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.CampaignInfo;
import lombok.Data;

import java.time.LocalDate;

/**
 * 캠패인 정보 : 표지이미지, 켐페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액
 *
 */
@Data
public class CampaignInfoSampleDto {
    private String campaignId;
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private String coverImagePath;

    public CampaignInfoSampleDto setCampaignInfo(CampaignInfo campaignInfo){
        campaignId = campaignInfo.getCampaignId();
        subject = campaignInfo.getSubject();
        charityName = campaignInfo.getCharityName();
        deadline = campaignInfo.getDeadline();
        currentAmount = campaignInfo.getCurrentAmount();
        goalAmount = campaignInfo.getGoalAmount();
        coverImagePath = campaignInfo.getCoverImagePath();
        return this;
    }
}
