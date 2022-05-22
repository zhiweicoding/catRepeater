package io.github.zhiweicoding.csw.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Created by zhiwei on 2022/3/20.
 */
@Getter
public enum FactoryEnum {

    DEFAULT(1, "演示厂家");

    @EnumValue
    @JsonValue
    private final int factoryId;
    private final String factoryName;

    FactoryEnum(int factoryId, String factoryName) {
        this.factoryId = factoryId;
        this.factoryName = factoryName;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public String getFactoryName(int factoryId) {
        for (FactoryEnum c : FactoryEnum.values()) {
            if (c.getFactoryId() == factoryId) {
                return c.factoryName;
            }
        }
        return "没有找到对应的厂家";
    }
}
