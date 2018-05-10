package com.jianpanmao.news.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianpanmao.common.entity.DataTablesRequestEntity;
import com.jianpanmao.common.entity.DataTablesResponseEntity;
import com.jianpanmao.news.entity.News;
import com.jianpanmao.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("news")
public class NewsController {


    @Autowired
    NewsService newsService;


    @RequestMapping(method = RequestMethod.GET,value = "list")
    public String list(){
        return "news/list";
    }

    @RequestMapping(method = RequestMethod.GET,value = "add")
    public String add(){
        return "news/add";
    }

    @RequestMapping(method = RequestMethod.GET,value = "edit")
    public String edit(@RequestParam("id") Integer id, Model model){
        model.addAttribute("id",id);
        return "news/edit";
    }


    @RequestMapping(method = RequestMethod.GET,value = "detail")
    public String detail(@RequestParam("id") Integer id, Model model){
        model.addAttribute("id",id);
        return "news/detail";
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Integer addNews(@RequestBody News news){
        return newsService.add(news);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Integer deleteNews(@RequestParam("id") Integer id){
        return newsService.remove(id);
    }



    @RequestMapping(method = RequestMethod.DELETE,value = "batch")
    @ResponseBody
    public Integer batchDelete(@RequestBody Integer[] ids){
        return newsService.removeBatch(ids);
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Integer putNews(@RequestBody News news){
        return newsService.update(news);
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public News getNews(@RequestParam("id") Integer id){
        return newsService.get(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "page")
    @ResponseBody
    public PageInfo getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,News news){
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsService.getAll(news);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    @RequestMapping(method = RequestMethod.POST,value = "datatables")
    @ResponseBody
    public Object datatables(@RequestBody DataTablesRequestEntity dataTablesRequestEntity){
        PageHelper.startPage(dataTablesRequestEntity.getStart()/ dataTablesRequestEntity.getLength()+1, dataTablesRequestEntity.getLength());
        List<News> list = newsService.getAll(null);
        PageInfo pageInfo = new PageInfo(list);
        DataTablesResponseEntity<News> responseEntity=new DataTablesResponseEntity(dataTablesRequestEntity.getDraw(),pageInfo.getTotal(),pageInfo.getTotal(),pageInfo.getList());
        return responseEntity;
    }



}
