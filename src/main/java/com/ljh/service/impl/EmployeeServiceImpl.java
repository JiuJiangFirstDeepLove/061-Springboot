package com.ljh.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljh.mapper.EmployeeMapper;
import com.ljh.pojo.Employee;
import com.ljh.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
