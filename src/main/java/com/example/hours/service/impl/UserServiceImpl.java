package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.entity.Role;
import com.example.hours.entity.User;
import com.example.hours.entity.UserRole;
import com.example.hours.exception.HourException;
import com.example.hours.mapper.RoleMapper;
import com.example.hours.mapper.UserMapper;
import com.example.hours.mapper.UserRoleMapper;
import com.example.hours.model.bo.UserInfo;
import com.example.hours.model.pagination.UserPage;
import com.example.hours.service.UserService;
import com.example.hours.utils.HolderUserUtils;
import com.example.hours.utils.SecurityUtils;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    @Override
    public UserInfo getMyInfo() {
        // 当前登录用户ID
        Integer loginUserId = SecurityUtils.getUserId();
        if (Objects.nonNull(loginUserId)) {
            return getUserInfoById(loginUserId);
        }
        return null;
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfoById(Integer userId) {
        // 获取用户信息
        User user = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getId, userId)
                        .select(
                                User::getUsername, User::getEmail,
                                User::getPhone, User::getSex, User::getCreateTime
                        )
        );
        // 获取用户关联的角色
        Integer roleId = userRoleMapper.selectRoleId(userId);
        // 获取角色信息
        Role role = roleMapper.selectOne(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getId, roleId)
                        .select(Role::getId, Role::getName)
        );
        // 封装 userInfo 对象
        return UserInfo.builder()
                .userId(userId)
                .username(user.getUsername()).email(user.getEmail()).phone(user.getPhone()).sex(user.getSex())
                .roleId(role.getId())
                .build();
    }

    /**
     * 分页，批量获取用户信息
     * @param userPage 请求信息
     * @return 用户信息
     */
    @Override
    public PageResult getUserInfos(UserPage userPage) {
        // TODO 添加过滤条件
        IPage<User> userIPage = this.baseMapper.selectPage(
                PageUtils.initPage(userPage),
                new LambdaQueryWrapper<User>()
                        .eq(Objects.nonNull(userPage.getUsername()), User::getUsername, userPage.getUsername())
                        .eq(Objects.nonNull(userPage.getPhone()), User::getPhone, userPage.getPhone())
        );

        return new PageResult(userIPage);
    }

    /**
     * 添加用户
     * @param user 用户信息
     */
    @Override
    @Transactional
    public void insertUser(User user) {
        // 添加用户
        if (!checkUsernameUnique(user)) {
            throw new HourException("新增用户'" + user.getUsername() + "'失败，用户名已存在");
        }

        if (!checkEmailUnique(user)) {
            throw new HourException("新增用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }

        if (StringUtils.isNotBlank(user.getPhone()) && !checkPhoneUnique(user)) {
            throw new HourException("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        }

        if (Objects.isNull(user.getSex())) {
            user.setSex(EntityConstant.USER_SEX_UNKNOWN);
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setDelFlag(EntityConstant.COMMON_UNDELETED);
        this.save(user);

        // 添加角色关联
        UserRole userRole = new UserRole(user.getId(), user.getRoleId());
        userRoleMapper.save(userRole);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    @Override
    @Transactional
    public void updateUser(User user) {
        if (StringUtils.isNotBlank(user.getEmail()) && !checkEmailUnique(user)) {
            throw new HourException("修改用户失败，邮箱账号已被其他用户使用");
        }

        if (StringUtils.isNotBlank(user.getPhone()) && !checkPhoneUnique(user)) {
            throw new HourException("修改用户失败，手机号码已被其他用户使用");
        }

        if (Objects.nonNull(user.getRoleId())) {
            // 当前登录用户不是管理员，拒绝修改用户角色
            if (!isAdmin(SecurityUtils.getUserId())) {
                throw new HourException("修改用户失败，权限不足，不能修改用户角色");
            }
            userRoleMapper.updateRoleId(user.getId(), user.getRoleId());
        }
        this.updateById(user);
    }

    /**
     * 判断用户是否是管理员
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public boolean isAdmin(Integer userId) {
        Integer roleId = userRoleMapper.selectRoleId(userId);
        return EntityConstant.ROLE_ADMIN_ID.equals(roleId);
    }

    /**
     * 删除用户信息
     * @param userIds 用户id列表
     */
    @Override
    @Transactional
    public void deleteUser(List<Integer> userIds) {
        // 批量删除用户与角色的关联信息
        userRoleMapper.deleteBatchUserIds(userIds);
        // 批量删除用户信息
        this.baseMapper.deleteBatchIds(userIds);
    }

    /**
     * 校验邮箱是否唯一
     * @param user 用户信息
     * @return 结果
     */
    private boolean checkEmailUnique(User user) {
        User info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, user.getEmail())
                        .select(User::getId)
        );

        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     * @param user 用户信息
     * @return 结果
     */
    private boolean checkUsernameUnique(User user) {
        User info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, user.getUsername())
                        .select(User::getId)
        );

        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验手机号是否唯一
     * @param user 用户信息
     * @return 结果
     */
    private boolean checkPhoneUnique(User user) {
        User info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getPhone, user.getPhone())
                        .select(User::getId)
        );

        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }
}
