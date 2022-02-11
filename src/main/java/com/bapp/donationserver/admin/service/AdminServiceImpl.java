package com.bapp.donationserver.admin.service;

import com.bapp.donationserver.admin.repository.CategoryRepository;
import com.bapp.donationserver.admin.domain.Category;
import com.bapp.donationserver.admin.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategoryList() {
        List<CategoryDto> dtoList = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> dtoList.add(category.getDto()));
        return dtoList;
    }

    @Override
    public void registerCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void modifyCategory(String categoryName, CategoryDto categoryDto) {
        Category category = categoryRepository.findByName(categoryName);
        category.setDto(categoryDto);
    }

    @Override
    public void deleteCategory(String categoryName) {
        categoryRepository.delete(categoryName);
    }

    @Override
    public void changeCampaignAcceptTo(String campaignId, Boolean status) {

    }
}
