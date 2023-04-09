package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.User;
import com.example.hours.model.bo.UserInfo;
import com.example.hours.model.pagination.UserPage;
import com.example.hours.model.vo.AuthUserVO;
import com.example.hours.model.vo.UserPwdVO;
import com.example.hours.utils.page.PageResult;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    UserInfo getMyInfo();

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfo getUserInfoById(Integer userId);

    /**
     * 分页，批量获取用户信息
     * @param userPage 请求信息
     * @return 用户信息
     */
    PageResult getUserInfos(UserPage userPage);

    /**
     * 添加用户
     * @param user 用户信息
     */
    void insertUser(User user);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUser(User user);

    /**
     * 根据用户ID判断用户是否是管理员
     * @param userId 用户ID
     * @return 结果
     */
    boolean isAdmin(Integer userId);

    /**
     * 删除用户信息
     * @param userIds 用户id列表
     */
    void deleteUsers(List<Integer> userIds);

    /**
     * 重置用户密码
     * @param userPwdVO 用户密码信息
     */
    void resetPassword(UserPwdVO userPwdVO);

    /**
     * 根据角色ID查找关联的角色信息分页列表
     * @param userPage 用户分页信息
     * @return 用户信息
     */
    PageResult selectAllocatedList(UserPage userPage);

    /**
     * 根据角色ID查找未分配该角色的用户列表
     * @param userPage 用户分页信息
     * @return 用户信息
     */
    PageResult selectUnallocatedList(UserPage userPage);
}
