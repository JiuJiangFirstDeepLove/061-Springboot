package com.ljh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljh.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from role where role_key = #{role_key}")
    Integer selectByRoleKey(@Param("role_key") String role);
}
