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
    public void update(String before, String after) {

        String query = "update Category c set c.name = :after where c.name=:before";
        em.createQuery(query).setParameter("before", before)
                .setParameter("after", after)
                .executeUpdate();
    }

    @Override
    public void delete(Category category) {
        //수정 필요
        em.remove(category);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category as c", Category.class).getResultList();
    }
}
