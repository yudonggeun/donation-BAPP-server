package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Category;

import java.util.List;

public interface CategoryRepository {

    void save(Category data);

    Category findByName(String categoryName);

    void delete(String categoryName);

    List<Category> findAll();
}
