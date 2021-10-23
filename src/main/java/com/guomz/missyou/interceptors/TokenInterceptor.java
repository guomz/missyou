package com.guomz.missyou.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.guomz.missyou.annotations.ScopeLevel;
import com.guomz.missyou.exception.ForbiddenException;
import com.guomz.missyou.exception.UnAuthenticatedException;
import com.guomz.missyou.util.JwtUtil;
import com.guomz.missyou.util.LocalUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer level = getLevel(handler);
        //没有设置权限直接放行
        if (level == null){
            return true;
        }
        //验证header中的token
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.length() > 7){
            String token = authHeader.substring(7);
            Map<String, Claim> claimMap = jwtUtil.decodeToken(token);
            //获取权限

            if (level != null && !hasPermission(claimMap, level)){
                log.error("用户权限不足");
                throw new ForbiddenException(10005);
            }
            //获取用户ID
            LocalUserUtil.setUserId(getUserId(claimMap));
//            request.setAttribute("userId", getUserId(claimMap));
            return true;
        }else {
            log.error("令牌不正确");
            log.error("访问路径：{}", request.getServletPath());
            throw new UnAuthenticatedException(10004);
        }
    }

    /**
     * 获取token中的用户id
     * @param claimMap
     * @return
     */
    private Long getUserId(Map<String, Claim> claimMap){
        return claimMap.get("userId").asLong();
    }

    /**
     * 比较用户权限与接口权限
     * @param claimMap
     * @param level
     * @return
     */
    private boolean hasPermission(Map<String, Claim> claimMap, Integer level){
        Integer levelFromToken = claimMap.get("scopeLevel").asInt();
        if (levelFromToken == null){
            log.error("token中无权限");
            throw new UnAuthenticatedException(10004);
        }
        return levelFromToken.compareTo(level) >= 0;
    }

    /**
     * 获取方法权限注解上的权限
     * @param handler
     * @return
     */
    private Integer getLevel(Object handler){
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethodAnnotation(ScopeLevel.class);
            if (scopeLevel != null){
                return scopeLevel.value();
            }
            return null;
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        LocalUserUtil.clear();
    }
}
