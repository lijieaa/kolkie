package com.jianpanmao.news.controller;

import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("news")
public class NewsController {


    @Autowired
    NewsService newsService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Integer addNews(@RequestBody News news){
        return newsService.insert(news);
    }

}
