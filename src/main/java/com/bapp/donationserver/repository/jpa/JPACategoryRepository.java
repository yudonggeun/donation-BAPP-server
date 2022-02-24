package com.bapp.donationserver.repository.jpa;

import com.bapp.donationserver.data.Category;
import com.bapp.donationserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
@Primary
@RequiredArgsConstructor
public class JPACategoryRepository implements CategoryRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Category data) {
        em.persist(data);
    }

    @Override
    public Category findByName(String categoryName) {
        return em.find(Category.class, categoryName);
    }

    @Override
    public void delete(String categoryName) {
        //수정 필요
        Category category = em.find(Category.class, categoryName);
        em.remove(category);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select i from i Category").getResultList();
    }
}