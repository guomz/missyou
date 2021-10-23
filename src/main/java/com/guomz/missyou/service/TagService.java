package com.guomz.missyou.service;

import com.guomz.missyou.entity.Tag;
import com.guomz.missyou.repository.TagRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagService {

    @Resource
    private TagRepository tagRepository;

    public List<Tag> getByType(Integer type){
        return tagRepository.findByType(type);
    }
}
