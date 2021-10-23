package com.guomz.missyou.util;

import java.util.HashMap;
import java.util.Map;

public class LocalUserUtil {

    private static ThreadLocal<Map<String, Long>> threadLocal = new ThreadLocal();

    public static void setUserId(Long id){
        Map<String, Long> userMap = new HashMap<>();
        userMap.put("userId", id);
        LocalUserUtil.threadLocal.set(userMap);
    }

    public static Long getUserId(){
        Map<String, Long> userMap = LocalUserUtil.threadLocal.get();
        return userMap.get("userId");
    }

    public static void clear(){
        LocalUserUtil.threadLocal.remove();
    }
}
