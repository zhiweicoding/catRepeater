package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配置表
 * @TableName t_config
 */
@TableName(value ="t_config")
@Data
@Accessors(chain = true)
public class ConfigBean extends BaseMapperBean implements Serializable {
    /**
     * 
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;

    /**
     * 
     */
    @TableField(value = "config_name")
    private String configName;

    /**
     * 
     */
    @TableField(value = "config_msg")
    private String configMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567891L;
}