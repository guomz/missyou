package com.guomz.missyou.util;

public class KeyUtil {

    /**
     * 生成随机key
     * @return
     */
    public static String generateKey(){
        int randomNum = (int)(Math.random()*9000) + 1000;
        return System.currentTimeMillis() + randomNum + "";
    }
}
