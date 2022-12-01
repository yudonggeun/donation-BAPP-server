package com.bapp.donationserver.service.category;

import com.bapp.donationserver.repository.CategoryRepository;
import com.bapp.donationserver.entity.Category;
import com.bapp.donationserver.data.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return (List<CategoryDto>) categoryRepository.findAll().stream().map(category -> new CategoryDto(category.getName()));
    }

    @Override
    public void registerCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void modifyCategory(String before, CategoryDto after) {
        categoryRepository.update(before, after.getName());
    }

    @Override
    public void deleteCategory(String categoryName) {
        categoryRepository.deleteById(categoryName);
    }

}
