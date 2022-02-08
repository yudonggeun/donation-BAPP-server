package com.bapp.donationserver.data;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 표지이미지, 상세이미지, 캠페인 제목, 재단 이름, 마감일, 현재 모금 금액, 목표 금액, 카테고리, 계획
 */
@Data
public class CampaignInfo {
    private String campaignId;
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private List<String> categories;
    private String coverImagePath;
    private String detailImagePath;
}
