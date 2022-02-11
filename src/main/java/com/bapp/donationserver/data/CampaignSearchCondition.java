package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import lombok.Data;

import java.util.List;

/**
 * 검색 조건 : 페이지 정보(시작 인덱스, 끝 인덱스), 단채명, 제목, 카테고리 중복, 관심
 */
@Data
public class CampaignSearchCondition {
    private Integer startIndex;
    private Integer endIndex;
    private String charityName;
    private String subject;
    private List<String> categories;
    private Boolean interest;
    private String userId;
    private MemberType memberType;

    public CampaignSearchCondition() {
        this.startIndex = 0;
        this.endIndex = 4;
    }

    public CampaignSearchConditionDto getDto() {
        CampaignSearchConditionDto dto = new CampaignSearchConditionDto();

        dto.setStartIndex(startIndex);
        dto.setEndIndex(endIndex);
        dto.setCharityName(charityName);
        dto.setSubject(subject);
        dto.setCategories(categories);
        dto.setInterest(interest);
        dto.setUserId(userId);

        return dto;
    }

    public void setDto(CampaignSearchConditionDto dto) {
        if(dto == null)
            return;
        setStartIndex(dto.getStartIndex());
        setEndIndex(dto.getEndIndex());
        setCharityName(dto.getCharityName());
        setSubject(dto.getSubject());
        setCategories(dto.getCategories());
        setInterest(dto.getInterest());
        setUserId(dto.getUserId());
    }
}
