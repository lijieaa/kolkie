package com.jianpanmao.news.service.impl;

import com.jianpanmao.common.service.impl.BaseServiceImpl;
import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.entity.NewsExample;
import com.jianpanmao.news.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class NewsServiceImpl extends BaseServiceImpl<News,NewsExample,Integer> implements NewsService {

}