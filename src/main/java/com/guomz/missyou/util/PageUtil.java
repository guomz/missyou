package com.guomz.missyou.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PageUtil {

    public static Pageable getPage(Integer start, Integer count, Sort sort){
        Integer page = start / count;
        Pageable pageable;
        if (sort == null){
            pageable = PageRequest.of(page, count, Sort.unsorted());
        }else {
            pageable = PageRequest.of(page, count, sort);
        }
        return pageable;
    }

}
