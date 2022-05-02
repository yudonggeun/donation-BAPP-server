package com.bapp.donationserver.service.category;

import com.bapp.donationserver.data.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    //카테고리 리스트 전체 조회
    List<CategoryDto> getCategoryList();
    //카테고리 등록
    void registerCategory(CategoryDto categoryDto);
    //카테고리 수정
    void modifyCategory(String before, CategoryDto after);
    //카테고리 삭제
    void deleteCategory(String categoryName);
}
