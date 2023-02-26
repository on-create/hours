package com.example.hours.handler;

import com.example.hours.bo.ResourceRoleBO;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.dao.MenuDao;
import com.example.hours.dao.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
public class MenuFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * 菜单角色列表
     */
    private static List<ResourceRoleBO> resourceRoleBOList;

    @Autowired
    private ResourceDao resourceDao;

    /**
     * 加载菜单角色信息
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    private void loadDataSource() {
        resourceRoleBOList = resourceDao.listResourceRoles();
    }

    /**
     * 清空接口角色信息
     */
    public void clearDataSource() {
        resourceRoleBOList = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 修改接口角色关系后重新加载
        if (CollectionUtils.isEmpty(resourceRoleBOList)) {
            this.loadDataSource();
        }

        FilterInvocation filterInvocation = (FilterInvocation) object;
        // 获取用户请求url
        String url = filterInvocation.getRequest().getRequestURI();
        String method = filterInvocation.getRequest().getMethod();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // TODO: 修改权限
        // 获取接口角色信息，若为匿名接口则放行，若无对应角色则禁止
        for (ResourceRoleBO resourceRoleBO : resourceRoleBOList) {
            if (antPathMatcher.match(resourceRoleBO.getUrl(), url) && method.equals(resourceRoleBO.getRequestMethod())) {
                // 允许匿名访问
                if (resourceRoleBO.getIsAnonymous() == EntityConstant.RESOURCE_ANONYMOUS) {
                    return SecurityConfig.createList("anonymous");
                }

                // 不允许匿名访问，获取角色列表
                List<String> roleList = resourceRoleBO.getRoleList();
                if (CollectionUtils.isEmpty(roleList)) {
                    return SecurityConfig.createList("db error");
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        return SecurityConfig.createList("error");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
