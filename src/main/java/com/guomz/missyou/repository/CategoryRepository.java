package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIsRoot(Integer isRoot);
}
