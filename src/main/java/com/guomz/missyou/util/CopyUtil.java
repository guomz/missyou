package com.guomz.missyou.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CopyUtil {

    /**
     * 拷贝list
     * @param tList
     * @param targetK
     * @param <T>
     * @param <K>
     * @return
     */
    public static<T,K> List<K> copyList(List<T> tList, Supplier<K> targetK){
        return tList.stream().map(item -> {
            K k = targetK.get();
            BeanUtils.copyProperties(item, k);
            return k;
        }).collect(Collectors.toList());
    }
}
