package com.example.demo_json_and_gson.repository;

import com.example.demo_json_and_gson.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT new com.example.demo_json_and_gson.repository.CategoryStatsProj(" +
            "c.name, size(p),avg(p.price), sum(p.price) )  FROM Category as c " +
            "join c.products as p " +
            "group by c " +
            "order by size(p) desc ")
    List<CategoryStatsProj> findCategoryStatus();
}
