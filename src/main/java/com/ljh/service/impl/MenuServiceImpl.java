package com.ljh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljh.mapper.MenuMapper;
import com.ljh.pojo.Menu;
import com.ljh.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>implements MenuService {
    @Override
    public List<Menu> findMenus(String username) {
        //vue搜索
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isBlank((username))) {
            queryWrapper.like("username", username);
        }
        //查询所有数据
        List<Menu> list = list(queryWrapper);
        //找到一级菜单,pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //通过一级菜单去渲染,找到一级菜单的子菜单,筛选所有数据中的pid=父级id就是二级菜单
        for (Menu menu : parentNodes) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
