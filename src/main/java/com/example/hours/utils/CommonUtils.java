package com.example.hours.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

public class CommonUtils {

    /**
     * 判断实体内的属性是否都为 null
     * @param obj 被检查的对象
     * @return true/false
     */
    public static boolean checkObjAllFieldsIsNull(Object obj) {
        // 如果对象为 null，直接返回 false
        if (obj == null) {
            return false;
        }

        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                // 如果属性名不为serialVersionUID，有一个属性值不为null，且值不是空字符串，就返回false
                boolean flag = !"serialVersionUID".equals(field.getName()) &&
                        field.get(obj) != null && StringUtils.isNotBlank(field.get(obj).toString());
                if (flag) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
