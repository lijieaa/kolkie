package com.jianpanmao.news.service.impl;

import com.jianpanmao.common.service.impl.BaseServiceImpl;
import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.entity.NewsExample;
import com.jianpanmao.news.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
public class NewsServiceImpl extends BaseServiceImpl<News,NewsExample,Integer> implements NewsService {

    @Override
    public List<News> getAll(News record) {
        NewsExample example=new NewsExample();
        if(null!=record.getTitle()&&record.getTitle().length()>0){
            example.createCriteria().andTitleLike(record.getTitle());
        }
        example.setOrderByClause("t_id desc");
        return super.getByExample(example);
    }
}