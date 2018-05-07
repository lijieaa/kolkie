package com.jianpanmao.news.dao;

import com.jianpanmao.common.dao.BaseDao;
import com.jianpanmao.news.entity.Project;
import com.jianpanmao.news.entity.ProjectExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseDao<Project, ProjectExample, Integer> {
}