package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @TableName t_factory
 */
@TableName(value = "t_factory")
@Data
@Accessors(chain = true)
public class FactoryBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "factory_id", type = IdType.AUTO)
//    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private Integer factoryId;

    /**
     * 区分那个部分，不同地区
     */
    @TableField(value = "factory_name")
    private String factoryName;

    /**
     *
     */
    @TableField(value = "is_use")
    private Integer isUse;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567893L;
}