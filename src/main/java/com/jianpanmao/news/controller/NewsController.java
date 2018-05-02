package com.jianpanmao.news.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Integer deleteNews(@RequestParam("id") Integer id){
        return newsService.deleteByPrimaryKey(id);
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Integer putNews(@RequestBody News news){
        return newsService.updateByPrimaryKey(news);
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public News getNews(@RequestParam("id") Integer id){
        return newsService.selectByPrimaryKey(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "page")
    @ResponseBody
    public PageInfo getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,News news){
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsService.selectAll(news);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
