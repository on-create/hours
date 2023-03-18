package com.example.hours.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.model.vo.UserVo;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;

import java.util.List;

public interface UserService extends IService<SysUser> {

    UserVo getUserVo();

    void updateUserInfo(SysUser sysUser);

    void addUser(SysUser sysUser);

    void sendCode(String email);

    // -----------------------------------------------------------------

    /**
     * 根据条件分页查询用户列表
     * @param params 分页信息
     * @param sysUser 用户信息
     * @return 用户信息集合分页结果
     */
    PageResult selectUserList(PageParams params, SysUser sysUser);

    SysUser selectUser(Integer userId);

    /**
     * 新增用户
     * @param sysUser 用户信息
     */
    void insertUser(SysUser sysUser);

    /**
     * 更新用户信息
     * @param sysUser 用户信息
     */
    void updateUser(SysUser sysUser);

    /**
     * 批量删除用户信息
     * @param userIds 需要删除的用户ID
     */
    void deleteUserByIds(List<Integer> userIds);

    /**
     * 重置用户密码
     * @param sysUser 用户信息
     */
    void resetPassword(SysUser sysUser);

    /**
     * 修改用户状态
     * @param sysUser 用户信息
     */
    void updateUserStatus(SysUser sysUser);

    /**
     * 给用户授权角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    void insertUserAuth(Integer userId, List<Integer> roleIds);

    /**
     * 根据用户编号获取授权角色
     * @param userId 用户编号
     * @return 用户角色列表
     */
    List<SysRole> getRolesById(Integer userId);

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param params 分页信息
     * @param sysUser 用户信息
     * @return 已分配用户角色列表
     */
    PageResult selectAllocatedList(PageParams params, SysUser sysUser);

    PageResult selectUnallocatedList(PageParams params, SysUser sysUser);
}
