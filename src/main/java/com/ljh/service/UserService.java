package com.ljh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljh.common.Result;
import com.ljh.controller.rxd.UserRXD;
import com.ljh.pojo.User;

public interface UserService extends IService<User>{
    User getUserByLogin(UserRXD userRXD);
    UserRXD login(UserRXD userRXD);
}
