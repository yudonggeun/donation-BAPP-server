package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.CampaignFullDto;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
//import com.sun.istack.NotNull;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

/**
 * 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
 */
//@Entity
@Data
public class Campaign {
//    @Id
    private String campaignId;
//    @NotNull
    private String subject;
//    @NotNull
    private String charityName;
//    @NotNull
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private List<String> categories;
    private String coverImagePath;
    private String detailImagePath;
    private Boolean isAccepted;

    public Campaign() {
    }

    public Campaign(String campaignId) {
        this.campaignId = campaignId;
        this.isAccepted = false;
        this.currentAmount = 0L;
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
