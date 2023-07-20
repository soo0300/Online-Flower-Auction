package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.domain.plant.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}