package com.example.hours.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.dao.RoleDao;
import com.example.hours.entity.Role;
import com.example.hours.exception.HourException;
import com.example.hours.service.RoleService;
import com.example.hours.model.vo.RoleVO;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;
import com.example.hours.utils.page.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    /*@Override
    public List<RoleVO> getRoleVOs(Integer status) {
        List<Role> roles = this.baseMapper.selectList(
                new LambdaQueryWrapper<Role>()
                        .eq(Role::getStatus, status)
                        .select(Role::getId, Role::getRoleName, Role::getRoleKey)
        );

        return roles.stream()
                .map(role -> {
                    RoleVO roleVO = new RoleVO();
                    BeanUtils.copyProperties(role, roleVO);
                    return roleVO;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 保存
        if (Objects.nonNull(roleVO.getStatus())) {
            Role role = Role.builder()
                    .roleName(roleVO.getName())
                    .roleKey(roleVO.getRoleKey())
                    .status(EntityConstant.COMMON_ENABLE)
                    .delFlag(EntityConstant.COMMON_UNDELETED)
                    .build();
            this.save(role);
            return;
        }

        // 更新
        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);
        this.updateById(role);
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = this.baseMapper.selectUserCount(roleIdList);
        if (count > 0) {
            throw new HourException("该角色下存在用户");
        }
        this.baseMapper.deleteBatchIds(roleIdList);
    }*/

    /**
     * 根据条件分页查询角色数据
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public PageResult selectRoleList(Role role, PageParams params) {
        IPage<?> page = PageUtils.initPage(params);
        List<Role> roles = this.baseMapper.selectRoleList(page, role);
        return PageUtils.selectResult(page, roles);
    }
}
