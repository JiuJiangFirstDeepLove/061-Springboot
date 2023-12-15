package com.ljh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljh.common.Result;
import com.ljh.controller.rxd.UserRXD;
import com.ljh.mapper.RoleMapper;
import com.ljh.mapper.RoleMenuMapper;
import com.ljh.mapper.UserMapper;
import com.ljh.pojo.Menu;
import com.ljh.pojo.User;
import com.ljh.service.MenuService;
import com.ljh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;
    //获取角色唯一标识roleKey
    @Resource
    private RoleMapper roleMapper;
    //角色和菜单的关联关系id
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;

    @Override
    public UserRXD login(UserRXD userRXD) {
        User alone = getUserByLogin(userRXD);
        if (alone != null) {
            Integer id = alone.getId();
            userRXD.setId(id);
            //获取角色
            String role = alone.getRole();
            userRXD.setRole(role);
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userRXD.setMenus(roleMenus);
        }
        return userRXD;

    }

    @Override
    public User getUserByLogin(UserRXD userRXD) {
        //判断账号密码是否正确
        //避免使用字符串表示字段，防止运行时错误
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, userRXD.getAccount())
                .eq(User::getPassword, userRXD.getPassword()));
//        if (res == null) {
//            return Result.error("-1", "用户名或密码错误");
//        }
        return res;//使用@ResponseBody返回给前端的都是json数据
    }

    //获取当前角色的菜单列表
    private List<Menu> getRoleMenus(String roleKey) {
        //获取唯一标识roleKey
        Integer roleId = roleMapper.selectByRoleKey(roleKey);//通过唯一标识roleKey获取到了id
        //根据roleId查询出当前角色所有的menuIds菜单数组
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查询出所有菜单
        List<Menu> menus = menuService.findMenus("");//是空的时候不会进行模糊查询
        //筛选当前用户角色的菜单,过滤掉没有筛选的菜单
        List<Menu> roleMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {//如果权限菜单id包含pid就展示出来
                roleMenus.add(menu);
            }
            //子菜单
            List<Menu> children = menu.getChildren();
            //如果菜单id不包含子菜单pid就移除
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}
