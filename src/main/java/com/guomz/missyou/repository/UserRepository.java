package com.guomz.missyou.repository;

import com.guomz.missyou.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByOpenid(String openId);
}
