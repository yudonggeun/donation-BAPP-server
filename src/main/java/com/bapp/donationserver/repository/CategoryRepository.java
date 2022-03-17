package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Category;

import java.util.List;

public interface CategoryRepository {

    void save(Category data);

    Category findByName(String categoryName);

    void update(String before, String after);

    void delete(Category category);

    List<Category> findAll();
}
