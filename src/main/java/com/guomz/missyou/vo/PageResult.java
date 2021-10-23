package com.guomz.missyou.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
public class PageResult<T,K> {

    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<K> items;

    public PageResult(Page<T> page, Supplier<K> kSupplier){
        this.total = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.page = page.getNumber();
        this.count = page.getSize();
        this.items = page.getContent().stream()
                .map(tItem -> {
                    K k = kSupplier.get();
                    BeanUtils.copyProperties(tItem, k);
                    return k;
                }).collect(Collectors.toList());
    }
}
