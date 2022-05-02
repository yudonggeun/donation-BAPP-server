package com.bapp.donationserver.data.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CampaignFullDto {
    private Long campaignId;
    private String campaignName;
    private String charityName;
    private LocalDate deadline;
    private String description;
    private Long currentAmount;
    private Long goalAmount;
    private List<String> categories = new ArrayList<>();
    private String coverImagePath;
    private String detailImagePath;
    private String reviewImagePath;
}
