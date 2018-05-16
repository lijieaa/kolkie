package com.jianpanmao.sys.service.impl;

import com.jianpanmao.common.service.impl.BaseServiceImpl;
import com.jianpanmao.sys.entity.*;
import com.jianpanmao.sys.dto.*;
import com.jianpanmao.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu,SysMenuExample,SysMenuDto,Integer> implements SysMenuService {

    @Override
    public int add(SysMenu record) {
        int i = super.add(record);
        if(record.getMenuParentId()==0){
            record.setPath(record.getMenuId()+"");
        }else {
            SysMenu pmenu = this.get(record.getMenuParentId());
            record.setPath(pmenu.getPath()+","+record.getMenuId());
        }
        this.update(record);
        return i;
    }
}