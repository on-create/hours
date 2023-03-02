package com.example.hours.common.constant;

public class EntityConstant {

    //- - - - - - - - - - - - - - - - - - - - -  Common 通用常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * 未删除
     */
    public static final int COMMON_UNDELETED = 0;

    /**
     * 已删除
     */
    public static final int COMMON_DELETED = 1;

    /**
     * 启用
     */
    public static final int COMMON_ENABLE = 0;

    /**
     * 禁用
     */
    public static final int COMMON_DISABLE = 1;

    //- - - - - - - - - - - - - - - - - - - - -  Activity 常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * 草稿
     */
    public static final int ACTIVITY_Draft = 0;

    /**
     * 待审批
     */
    public static final int ACTIVITY_PENDING_APPROVAL = 1;

    /**
     * 审批通过
     */
    public static final int ACTIVITY_APPROVE = 2;

    /**
     * 无人数限制
     */
    public static final int ACTIVITY_NO_MAXIMUM = 0;

    /**
     * 活动报名人数
     */
    public static final int ACTIVITY_NUMBER_ZERO = 0;

    /**
     * 每个用户最大的草稿数
     */
    public static final int ACTIVITY_DRAFT_MAX = 5;

    //- - - - - - - - - - - - - - - - - - - - -  RegisterActivity 常量 - - - - - - - - - - - - - - - - - - - -

    public static final String REGISTER_ACTIVITY_ID = "registerId: ";

    //- - - - - - - - - - - - - - - - - - - - -  Resource 常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * 不允许匿名访问
     */
    public static final int RESOURCE_NOT_ANONYMOUS = 0;

    /**
     * 允许匿名访问
     */
    public static final int RESOURCE_ANONYMOUS = 1;

    /**
     * 模块id
     */
    public static final int RESOURCE_MODULE_ID = 0;

}
