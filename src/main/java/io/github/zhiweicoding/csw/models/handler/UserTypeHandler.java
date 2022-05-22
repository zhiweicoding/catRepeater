package io.github.zhiweicoding.csw.models.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.zhiweicoding.csw.models.UserBean;

import java.io.IOException;

/**
 * @Created by zhiwei on 2022/3/21.
 */
public class UserTypeHandler extends JacksonTypeHandler {

    public UserTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        try {
            TypeReference<UserBean> typeReference = new TypeReference<UserBean>() {
            };
            return getObjectMapper().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
