package com.guomz.missyou.vo;

import com.guomz.missyou.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class TagSimplifyVo {
    private Long id;
    private String title;
    private String description;
    private Integer highlight;
    private Integer type;

    public TagSimplifyVo(Tag tag){
        BeanUtils.copyProperties(tag, this);
    }
}
