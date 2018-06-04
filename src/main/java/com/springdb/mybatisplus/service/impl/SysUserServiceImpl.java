package com.springdb.mybatisplus.service.impl;

import com.springdb.mybatisplus.entity.SysUser;
import com.springdb.mybatisplus.mapper.SysUserMapper;
import com.springdb.mybatisplus.service.MPSysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author niyue
 * @since 2018-06-03
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements MPSysUserService {

}
