package com.example.hours.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hours.entity.Role;
import com.example.hours.model.vo.RoleVO;
import com.example.hours.utils.page.PageParams;
import com.example.hours.utils.page.PageResult;

import java.util.List;

public interface RoleService extends IService<Role> {

    /*List<RoleVO> getRoleVOs(Integer status);

    void saveOrUpdateRole(RoleVO roleVO);*/

    //void deleteRoles(List<Integer> roleIdList);

    PageResult selectRoleList(Role role, PageParams params);
}
