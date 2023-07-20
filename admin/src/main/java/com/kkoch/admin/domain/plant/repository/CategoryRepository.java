package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.domain.plant.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {

}