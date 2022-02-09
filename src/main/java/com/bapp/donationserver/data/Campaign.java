package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
 */
@Data
public class Campaign {
    private String campaignId;
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private List<String> categories;
    private String coverImagePath;
    private String detailImagePath;
    private Boolean isAccepted;

    public Campaign() {
        this.campaignId = UUID.randomUUID().toString();
        this.isAccepted = false;
    }

    public CampaignSimpleDto getCampaignSimpleDto() {

        CampaignSimpleDto dto = new CampaignSimpleDto();

        dto.setCampaignId(campaignId);
        dto.setSubject(subject);
        dto.setCharityName(charityName);
        dto.setDeadline(deadline);
        dto.setCurrentAmount(currentAmount);
        dto.setGoalAmount(goalAmount);
        dto.setCoverImagePath(coverImagePath);
        return dto;
    }

    public CampaignFullDto getCampaignFullDto() {

        CampaignFullDto dto = new CampaignFullDto();

        dto.setSubject(subject);
        dto.setCharityName(charityName);
        dto.setDeadline(deadline);
        dto.setCurrentAmount(currentAmount);
        dto.setGoalAmount(goalAmount);
        dto.setCoverImagePath(coverImagePath);
        dto.setCategories(categories);
        dto.setDetailImagePath(detailImagePath);
        return dto;
    }

    public void setCampaignFullDto(CampaignFullDto dto){
        setSubject(dto.getSubject());
        setCharityName(dto.getCharityName());
        setDeadline(dto.getDeadline());
        setCurrentAmount(dto.getCurrentAmount());
        setGoalAmount(dto.getGoalAmount());
        setCoverImagePath(dto.getCoverImagePath());
        setCategories(dto.getCategories());
        setDetailImagePath(dto.getDetailImagePath());
    }

}
