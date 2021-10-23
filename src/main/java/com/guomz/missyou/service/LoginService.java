package com.guomz.missyou.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.guomz.missyou.entity.User;
import com.guomz.missyou.enums.Levels;
import com.guomz.missyou.exception.ServerErrorException;
import com.guomz.missyou.repository.UserRepository;
import com.guomz.missyou.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private WxMaService wxMaService;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public String getToken(String jsCode){
        //获取微信openid等信息
        WxMaJscode2SessionResult sessionResult;
        try {
            sessionResult  = wxMaService.jsCode2SessionInfo(jsCode);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("获取微信openid失败");
            throw new ServerErrorException(9999);
        }
        //处理用户登录
        User user = userRepository.findByOpenid(sessionResult.getOpenid());
        if (user == null){
            user = new User();
            user.setOpenid(sessionResult.getOpenid());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userRepository.save(user);
        }
        //生成令牌
        String jwtToken = jwtUtil.generateJwtToken(user.getId(), Levels.NORMAL.getCode());
        return jwtToken;
    }

    /**
     * 验证令牌
     * @param token
     * @return
     */
    public boolean verifyToken(String token){
        try {
            jwtUtil.decodeToken(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("令牌验证失败");
            return false;
        }

    }
}
