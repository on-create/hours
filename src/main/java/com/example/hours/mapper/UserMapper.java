package com.example.hours.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hours.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据条件分页查询已分配该角色的用户列表
     * @param iPage 分页信息
     * @param user 用户信息
     * @return 用户信息列表
     */
    List<User> selectAllocatedList(IPage<User> iPage, @Param("user") User user);

    /**
     * 根据条件分页查询未分配该角色的用户列表
     * @param iPage 分页信息
     * @param user 用户信息
     * @return 用户信息列表
     */
    List<User> selectUnallocatedList(IPage<User> iPage, @Param("user") User user);
}
