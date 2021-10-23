package com.guomz.missyou.util;

import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testGetToken(){
        System.out.println(jwtUtil.generateJwtToken(1L, 0));
    }

    @Test
    public void testDecode(){
        String token = jwtUtil.generateJwtToken(1L, 0);
        System.out.println(token);
        Map<String, Claim> claimMap = jwtUtil.decodeToken(token);
        for (Map.Entry<String, Claim> claimEntry : claimMap.entrySet()) {
            System.out.println(claimEntry.getKey());
            System.out.println(claimEntry.getValue().asLong());
        }
    }
}