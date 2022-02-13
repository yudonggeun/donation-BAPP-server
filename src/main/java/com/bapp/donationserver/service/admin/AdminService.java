package com.bapp.donationserver.service.admin;

import com.bapp.donationserver.data.dto.CategoryDto;

import java.util.List;

public interface AdminService {
    //카테고리 리스트 전체 조회
    List<CategoryDto> getCategoryList();
    //카테고리 등록
    void registerCategory(CategoryDto categoryDto);
    //카테고리 수정
    void modifyCategory(String categoryName, CategoryDto categoryDto);
    //카테고리 삭제
    void deleteCategory(String categoryName);
    //켐패인 승인 여부 변경
    void changeCampaignAcceptTo(String campaignId, Boolean status);
}
