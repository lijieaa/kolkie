package com.jianpanmao.news.controller;

import com.jianpanmao.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("news")
public class NewsController {


@Autowired
NewsService newsService;


@RequestMapping(method = RequestMethod.GET,value = "list")
public String list(){
return "news/news_list";
}

@RequestMapping(method = RequestMethod.GET,value = "add")
public String add(){
return "news/news_add";
}

@RequestMapping(method = RequestMethod.GET,value = "edit")
public String edit(@RequestParam("id") Integer id, Model model){
model.addAttribute("id",id);
return "news/news_edit";
}


@RequestMapping(method = RequestMethod.GET,value = "detail")
public String detail(@RequestParam("id") Integer id, Model model){
model.addAttribute("id",id);
return "news/news_detail";
}
}
