package com.example.hours.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.hours.enums.ZoneEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Configuration
public class AutoFillConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())));
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())));
    }
}
