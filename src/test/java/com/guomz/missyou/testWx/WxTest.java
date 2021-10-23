package com.guomz.missyou.testWx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WxTest {

    @Autowired
    private WxMaService wxMaService;

    @Test
    public void testGetSession() throws WxErrorException {
        WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo("033rln0w3Gd25X2mJf3w3ssyoW1rln0n");
        System.out.println(result);
    }
}
