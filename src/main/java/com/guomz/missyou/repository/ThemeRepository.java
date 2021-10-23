package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    Theme findThemeByName(String name);

    List<Theme> findThemesByNameIn(List<String> nameList);
}
