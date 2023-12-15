package com.ljh.common;

import lombok.Data;

@Data
public class Result<T> {
  //状态码 告诉前端返回数据成功与否
  private String code;
  //设置错误提示信息
  private String msg;
  //泛型数据，任何数据都可以被包含
  private T data;
  
  public Result() {
  }
  
  public Result(T data) {
    this.data = data;
  }
  
  public static Result success() {
    Result result = new Result<>();
    result.setCode("0");
    result.setMsg("成功");
    return result;
  }
  
  public static <T> Result<T> success(T data) {
    Result<T> result = new Result<>(data);
    result.setCode("0");
    result.setMsg("成功");
    return result;
  }
  
  //报错提示
  public static Result error(String code, String msg) {
    Result result = new Result();
    result.setCode(code);
    result.setMsg(msg);
    return result;
  }
  
}
