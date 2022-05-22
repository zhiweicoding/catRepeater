package io.github.zhiweicoding.csw.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @Created by zhiwei on 2022/3/20.
 */
@Configuration
@Slf4j
public class MysqlMetaObjectHandler implements MetaObjectHandler {

    /**
     * mybatis plus set 字段自动填充
     * insert
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("createTime", metaObject);
        Object modifyTime = this.getFieldValByName("modifyTime", metaObject);
        Object isDelete = this.getFieldValByName("isDelete", metaObject);
        if (createTime == null) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
        if (modifyTime == null) {
            this.strictInsertFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        }
        if (isDelete == null) {
            this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
        }
    }

    /**
     * mybatis plus set 字段自动填充
     * update
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifyTime = this.getFieldValByName("modifyTime", metaObject);
        if (modifyTime == null) {
            this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
