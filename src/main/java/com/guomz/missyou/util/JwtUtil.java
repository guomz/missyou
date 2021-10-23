package com.guomz.missyou.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.guomz.missyou.enums.Levels;
import com.guomz.missyou.exception.UnAuthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.salt}")
    private String salt = "123456";
    @Value("${jwt.expired-time}")
    private Integer expiredTime;

    /**
     * 生成token
     * @param userId
     * @param scopeLevel
     * @return
     */
    public String generateJwtToken(Long userId, Integer scopeLevel){
        Algorithm algorithm = Algorithm.HMAC256(salt);
        Date now = new Date();
        String token = JWT.create()
                .withIssuedAt(now)
                .withClaim("userId", userId)
                .withClaim("scopeLevel", scopeLevel == null ? Levels.NORMAL.getCode():scopeLevel)
                .withExpiresAt(getExpiredDate(now))
                .sign(algorithm);
        return token;
    }

    /**
     * 解密token
     * @param jwtToken
     * @return
     */
    public Map<String, Claim> decodeToken(String jwtToken){
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return decodedJWT.getClaims();
        }catch (Exception e){
            e.printStackTrace();
            log.error("校验令牌失败");
            throw new UnAuthenticatedException(10004);
        }
    }

    private Date getExpiredDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, expiredTime);
        return calendar.getTime();
    }
}
