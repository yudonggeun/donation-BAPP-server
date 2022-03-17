package com.bapp.donationserver.service.category;

import com.bapp.donationserver.data.dto.CategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Slf4j
@TestInstance(PER_CLASS)
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;

    //카테고리 리스트 전체 조회
    @Test
    void getCategoryList() {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        Assertions.assertThat(categoryList).isNotNull();
    }

    //카테고리 등록
    @Test
    void registerCategory() {
        CategoryDto categoryDto = new CategoryDto("new category name");
        categoryService.registerCategory(categoryDto);
        Assertions.assertThat(categoryService.getCategoryList()).contains(categoryDto);
    }

    //카테고리 수정
    @Test
    void modifyCategory() {
        String beforeName = "before";
        String afterName = "after";
        CategoryDto before = new CategoryDto(beforeName);
        CategoryDto after = new CategoryDto(afterName);
        categoryService.registerCategory(before);
        categoryService.modifyCategory(beforeName, after);

        List<CategoryDto> categoryList = categoryService.getCategoryList();
        Assertions.assertThat(categoryList).contains(after);
        Assertions.assertThat(categoryList).doesNotContain(before);
    }

    //카테고리 삭제
    @Test
    void deleteCategory() {
        CategoryDto categoryDto = new CategoryDto("new category name");
        categoryService.registerCategory(categoryDto);

        categoryService.deleteCategory(categoryDto.getName());

        List<CategoryDto> categoryList = categoryService.getCategoryList();
        Assertions.assertThat(categoryList).doesNotContain(categoryDto);
    }
}