package com.ljh.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljh.mapper.RoleMapper;
import com.ljh.mapper.RoleMenuMapper;
import com.ljh.pojo.Menu;
import com.ljh.pojo.Role;
import com.ljh.pojo.RoleMenu;
import com.ljh.service.MenuService;
import com.ljh.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>implements RoleService {
    @Resource
    MenuService menuService;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {

        // 先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) { // 二级菜单 并且传过来的menuId数组里面没有它的父级id
                // 那么我们就得补上这个父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }
    @Override
    public List<Integer> getRoleMenu(Integer roleId) {

        return roleMenuMapper.selectByRoleId(roleId);
    }
}
