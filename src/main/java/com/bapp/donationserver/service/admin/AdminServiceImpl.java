package com.bapp.donationserver.service.admin;

import com.bapp.donationserver.repository.CategoryRepository;
import com.bapp.donationserver.data.Category;
import com.bapp.donationserver.data.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
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
