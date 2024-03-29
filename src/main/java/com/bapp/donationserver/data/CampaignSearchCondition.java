package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.CampaignSearchConditionDto;
import com.bapp.donationserver.data.type.MemberType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 검색 조건 : 페이지 정보(시작 인덱스, 끝 인덱스), 단채명, 제목, 카테고리 중복, 관심
 */
@Data
@ToString
public class CampaignSearchCondition {
    private Integer startIndex;
    private Integer maxResult;
    private String charityName;
    private String subject;
    private List<String> categories;
    private MemberType memberType;

    public CampaignSearchCondition() {
        this.startIndex = 0;
        this.maxResult = 100;
    }

    public void setDto(CampaignSearchConditionDto dto) {
        if(dto == null)
            return;
        setStartIndex(dto.getStartIndex());
        setMaxResult(dto.getEndIndex());
        setCharityName(dto.getCharityName());
        setSubject(dto.getSubject());
        setCategories(dto.getCategories());
    }
}
