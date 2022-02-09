package com.bapp.donationserver.data.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CampaignFullDto {
    private String subject;
    private String charityName;
    private LocalDate deadline;
    private Long currentAmount;
    private Long goalAmount;
    private List<String> categories;
    private String coverImagePath;
    private String detailImagePath;
}
