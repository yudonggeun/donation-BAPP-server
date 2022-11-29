package com.bapp.donationserver.repository;

import com.bapp.donationserver.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String name);

    @Query("update Category c set c.name = :after where c.name=:before")
    void update(@Param("before") String before, @Param("after") String after);
}
