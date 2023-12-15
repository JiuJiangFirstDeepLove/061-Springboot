package com.ljh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljh.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> findMenus(String username);
}
