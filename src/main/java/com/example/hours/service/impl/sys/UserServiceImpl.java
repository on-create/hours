package com.example.hours.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.CommonConstant;
import com.example.hours.common.constant.RabbitMQConstant;
import com.example.hours.common.constant.RedisConstant;
import com.example.hours.dao.UserDao;
import com.example.hours.dao.UserRoleDao;
import com.example.hours.entity.sys.SysRole;
import com.example.hours.entity.sys.SysUser;
import com.example.hours.entity.sys.UserRole;
import com.example.hours.exception.HourException;
import com.example.hours.service.sys.RoleService;
import com.example.hours.service.sys.UserService;
import com.example.hours.utils.CodeUtils;
import com.example.hours.utils.HolderUserUtils;
import com.example.hours.utils.RedisKeyUtils;
import com.example.hours.utils.RedisUtils;
import com.example.hours.model.vo.EmailVo;
import com.example.hours.model.vo.UserVo;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserVo getUserVo() {
        // TODO 获取当前用户id
        SysUser sysUser = this.getById(2);
        if (sysUser == null) {
            // TODO 抛出异常，交由全局异常处理
            throw new RuntimeException("用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        return userVo;
    }

    @Override
    public void updateUserInfo(SysUser sysUser) {
        sysUser.setId(2);
        System.out.println(sysUser);
        this.update(
                sysUser,
                new UpdateWrapper<SysUser>().eq("id", sysUser.getId())
        );
    }

    @Override
    public void addUser(SysUser sysUser) {
        String email = sysUser.getEmail();
        if (this.getBaseMapper().getUserIdByEmail(email) != null) {
            throw new RuntimeException("该邮箱已使用");
        }
        this.save(sysUser);
    }

    @Override
    public void sendCode(String email) {
        // 生成 6 位随机验证码
        String code = CodeUtils.getRandomCode();
        // 发送验证码
        EmailVo emailVo = EmailVo.builder()
                .email(email)
                .subject("验证码")
                .content("您的验证码为 " + code + " ，有效期15分钟，请不要告诉他人!")
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_EMAIL_EXCHANGE, RabbitMQConstant.EMAIL_REGISTER_ROUTING_KEY , emailVo);
        redisUtils.set(RedisKeyUtils.codeKey(email), code, RedisConstant.CODE_EXPIRE_TIME);
    }

    /**
     * 根据条件分页查询用户列表
     * @param sysUser 用户信息
     * @param params 分页信息
     * @return 用户信息集合分页结果
     */
    @Override
    public PageResult selectUserList(PageParams params, SysUser sysUser) {
        IPage<?> page = PageUtils.initPage(params);
        List<SysUser> sysUsers = this.baseMapper.selectUserList(page, sysUser);
        return PageUtils.selectResult(page, sysUsers);
    }

    // TODO: 添加查询信息
    @Override
    public SysUser selectUser(Integer userId) {
        checkUserDataScope(userId);
        List<SysRole> sysRoles = roleService.selectRolesAll();
        int id = Objects.nonNull(userId) ? userId : HolderUserUtils.getLoginUserId();
        return this.baseMapper.selectById(id);
    }

    /**
     * 新增用户
     * @param sysUser 用户信息
     */
    @Override
    public void insertUser(SysUser sysUser) {
        if (!checkUserNameUnique(sysUser)) {
            throw new HourException("新增用户'" + sysUser.getUsername() + "'失败，登录账号已存在");
        }

        if (StringUtils.isNotBlank(sysUser.getPhone()) && !checkPhoneUnique(sysUser)) {
            throw new HourException("新增用户'" + sysUser.getUsername() + "'失败，手机号码已存在");
        }

        if (StringUtils.isNotBlank(sysUser.getEmail()) && !checkEmailUnique(sysUser)) {
            throw new HourException("新增用户'" + sysUser.getUsername() + "'失败，邮箱账号已存在");
        }
        sysUser.setCreateBy(HolderUserUtils.getLoginUserName());
        sysUser.setPassword(HolderUserUtils.encryptPassword(sysUser.getPassword()));
        this.save(sysUser);
    }

    /**
     * 校验用户名称是否唯一
     * @param sysUser 用户信息
     * @return 结果
     */
    private boolean checkUserNameUnique(SysUser sysUser) {
        SysUser info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, sysUser.getUsername())
                        .select(SysUser::getId)
        );
        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     * @param sysUser 用户信息
     * @return 是否唯一
     */
    private boolean checkPhoneUnique(SysUser sysUser) {
        SysUser info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getPhone, sysUser.getPhone())
                        .select(SysUser::getId)
        );
        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验email是否唯一
     * @param sysUser 用户信息
     * @return 是否唯一
     */
    private boolean checkEmailUnique(SysUser sysUser) {
        SysUser info = this.baseMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getEmail, sysUser.getEmail())
                        .select(SysUser::getId)
        );
        if (Objects.nonNull(info)) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 更新用户信息
     * @param sysUser 用户信息
     */
    @Override
    public void updateUser(SysUser sysUser) {
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser.getId());
        if (StringUtils.isNotBlank(sysUser.getUsername()) && !checkUserNameUnique(sysUser)) {
            throw new HourException("修改用户'" + sysUser.getUsername() + "'失败，登录账号已存在");
        }

        if (StringUtils.isNotBlank(sysUser.getPhone()) && !checkPhoneUnique(sysUser)) {
            throw new HourException("修改用户'" + sysUser.getUsername() + "'失败，手机号码已存在");
        }

        if (StringUtils.isNotBlank(sysUser.getEmail()) && !checkEmailUnique(sysUser)) {
            throw new HourException("修改用户'" + sysUser.getUsername() + "'失败，邮箱账号已存在");
        }
        sysUser.setUpdateBy(HolderUserUtils.getLoginUserName());
        this.updateById(sysUser);
    }

    /**
     * 校验用户是否允许操作
     * @param sysUser 用户信息
     */
    private void checkUserAllowed(SysUser sysUser) {
        if (Objects.nonNull(sysUser.getId()) && sysUser.isAdmin()) {
            throw new HourException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     * @param userId 用户id
     */
    private void checkUserDataScope(Integer userId) {
        if (!SysUser.isAdmin(HolderUserUtils.getLoginUserId())) {
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            // TODO 查询权限校验
            // throw new HourException("没有权限访问用户数据！");
        }
    }

    /**
     * 批量删除用户信息
     * @param userIds 需要删除的用户ID
     */
    @Override
    @Transactional
    public void deleteUserByIds(List<Integer> userIds) {
        if (userIds.contains(HolderUserUtils.getLoginUserId())) {
            throw new HourException("不能删除当前用户");
        }
        for (Integer userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleDao.deleteBatchUserRole(userIds);
        this.baseMapper.deleteBatchIds(userIds);
    }

    /**
     * 重置用户密码
     * @param sysUser 用户信息
     */
    @Override
    public void resetPassword(SysUser sysUser) {
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser.getId());
        sysUser.setPassword(HolderUserUtils.encryptPassword(sysUser.getPassword()));
        sysUser.setUpdateBy(HolderUserUtils.getLoginUserName());
        this.updateById(sysUser);
    }

    /**
     * 修改用户状态
     * @param sysUser 用户信息
     */
    @Override
    public void updateUserStatus(SysUser sysUser) {
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser.getId());
        sysUser.setUpdateBy(HolderUserUtils.getLoginUserName());
        this.updateById(sysUser);
    }

    /**
     * 给用户授权角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    @Override
    @Transactional
    public void insertUserAuth(Integer userId, List<Integer> roleIds) {
        checkUserDataScope(userId);
        userRoleDao.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 新增用户角色信息
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    private void insertUserRole(Integer userId, List<Integer> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<UserRole> list = roleIds.stream()
                    .map(roleId ->
                            UserRole.builder()
                                .userId(userId)
                                .roleId(roleId)
                                .build()
                    )
                    .collect(Collectors.toList());
            userRoleDao.addBatchUserRole(list);
        }
    }

    /**
     * 根据用户编号获取授权角色
     * @param userId 用户编号
     * @return 用户角色列表
     */
    @Override
    public List<SysRole> getRolesById(Integer userId) {
        // TODO 逻辑待修改
        List<SysRole> sysRoles = roleService.selectRoleByUserId(userId);
        return sysRoles;
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param params 分页信息
     * @param sysUser 用户信息
     * @return 已分配用户角色列表
     */
    @Override
    public PageResult selectAllocatedList(PageParams params, SysUser sysUser) {
        IPage<?> page = PageUtils.initPage(params);
        List<SysUser> sysUsers = this.baseMapper.selectAllocatedList(page, sysUser);
        return PageUtils.selectResult(page, sysUsers);
    }

    @Override
    public PageResult selectUnallocatedList(PageParams params, SysUser sysUser) {
        IPage<?> page = PageUtils.initPage(params);
        List<SysUser> sysUsers = this.baseMapper.selectUnallocatedList(page, sysUser);
        return PageUtils.selectResult(page, sysUsers);
    }
}
