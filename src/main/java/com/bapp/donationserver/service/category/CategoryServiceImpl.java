package com.bapp.donationserver.service.category;

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
public class CategoryServiceImpl implements CategoryService {

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
        categoryRepository.update(categoryName, categoryDto.getName());
    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        categoryRepository.delete(category);
    }

}
