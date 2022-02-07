package com.bapp.donationserver.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaignInfo {
    private String campaignId;
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private String CoverImagePath;
}
