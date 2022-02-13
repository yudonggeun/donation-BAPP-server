package com.bapp.donationserver.repository.memory;

import com.bapp.donationserver.data.Category;
import com.bapp.donationserver.repository.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Repository
public class MemoryCategoryRepository implements CategoryRepository {

    private final Map<String, Category> db = new HashMap<>();

    @Override
    public void save(Category data) {
        db.put(data.getName(), data);
    }

    @Override
    public Category findByName(String categoryName) {
        return db.get(categoryName);
    }

    @Override
    public void delete(String categoryName) {
        db.remove(categoryName);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) db.values();
    }
}
