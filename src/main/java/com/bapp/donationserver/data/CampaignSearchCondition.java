package com.bapp.donationserver.data;

import lombok.Data;

import java.util.List;

/**
 * 검색 조건 : 페이지 정보(시작 인덱스, 끝 인덱스), 단채명, 제목, 카테고리 중복, 관심
 *
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
}
