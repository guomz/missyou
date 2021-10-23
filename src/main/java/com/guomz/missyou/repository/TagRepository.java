package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByType(Integer type);
}
