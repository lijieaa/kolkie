package com.jianpanmao.news.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianpanmao.common.entity.DataTablesResponseEntity;
import com.jianpanmao.news.dto.NewsDto;
import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsRestController {


    @Autowired
    NewsService newsService;

    @RequestMapping(method = RequestMethod.POST)
    public Integer post(@Valid @RequestBody News news){
        return newsService.add(news);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Integer delete(@RequestParam("id") Integer id){
        return newsService.remove(id);
    }



    @RequestMapping(method = RequestMethod.DELETE,value = "batch")
    public Integer batchDelete(@RequestBody Integer[] ids){
        return newsService.removeBatch(ids);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public Integer put(@RequestBody News news){
        return newsService.update(news);
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public News get(@RequestParam("id") Integer id){
        return newsService.get(id);
    }


    @RequestMapping(method = RequestMethod.GET,value = "page")
    public Object page(@RequestParam(value = "pageNum",defaultValue = "1",required = true) Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10",required = true) Integer pageSize,
                             @RequestParam(value = "draw",required = false) Integer draw,
                             NewsDto newsDto){

        PageHelper.startPage(pageNum,pageSize);

        List<News> list = newsService.getByDto(newsDto);
        PageInfo pageInfo = new PageInfo(list);

        //draw 不等于空是datatables分页
        if(draw!=null){
            DataTablesResponseEntity<News> responseEntity=new DataTablesResponseEntity(draw,pageInfo.getTotal(),pageInfo.getTotal(),pageInfo.getList());
            return responseEntity;
        }else{
            return pageInfo;
        }

    }



}
