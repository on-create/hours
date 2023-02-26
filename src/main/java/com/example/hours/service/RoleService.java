package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Role;
import com.example.hours.vo.RoleVO;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<RoleVO> getRoleVOs(Integer status);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> roleIdList);
}
