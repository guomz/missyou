package com.guomz.missyou.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.missyou.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "sku")
@Where(clause = "delete_time is null and online = 1")
public class Sku {
    @Id
    private Long id;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    private String img;

    private String title;

    private Long spuId;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String code;

    private Integer stock;

    private Long categoryId;

    private Long rootCategoryId;

    private String specs;

    public void setSpecs(List<Spec> specList){
        String jsonStr = JsonUtil.objectToJson(specList);
        this.specs = jsonStr;
    }

    public List<Spec> getSpecs(){
        if (StringUtils.isBlank(this.specs)){
            return new ArrayList<>();
        }
        return JsonUtil.jsonToObject(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public BigDecimal getActualPrice(){
        return this.discountPrice == null ? this.price : this.discountPrice;
    }

    public List<String> getSpecValues(){
        List<Spec> specList = this.getSpecs();
        return specList.stream().map(Spec::getValue).collect(Collectors.toList());
    }
}