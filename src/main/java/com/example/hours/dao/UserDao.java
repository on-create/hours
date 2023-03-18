package com.example.hours.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<SysUser> {

    Integer getUserIdByEmail(@Param("email") String email);

    //-------------------------------

    /**
     * 根据条件分页查询用户列表
     * @param page 分页信息
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(IPage<?> page, @Param("sysUser") SysUser sysUser);

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param page 分页信息
     * @param sysUser 用户信息
     * @return 已分配用户角色列表
     */
    List<SysUser> selectAllocatedList(IPage<?> page, @Param("sysUser") SysUser sysUser);

    List<SysUser> selectUnallocatedList(IPage<?> page, @Param("sysUser") SysUser sysUser);
}
