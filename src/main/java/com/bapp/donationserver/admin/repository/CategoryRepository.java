package com.bapp.donationserver.admin.repository;

import com.bapp.donationserver.admin.domain.Category;

import java.util.List;

public interface CategoryRepository {

    void save(Category data);

    Category findByName(String categoryName);

    void delete(String categoryName);

    List<Category> findAll();
}
