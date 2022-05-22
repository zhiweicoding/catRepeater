package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 意见表
 * @TableName t_advice
 */
@TableName(value ="t_advice")
@Data
@Accessors(chain = true)
public class AdviceBean extends BaseMapperBean implements Serializable {
    /**
     * 
     */
    @TableId(value = "advice_id", type = IdType.AUTO)
    private Integer adviceId;

    /**
     * 
     */
    @TableField(value = "advice_name")
    private String adviceName;

    /**
     * 
     */
    @TableField(value = "advice_phone")
    private String advicePhone;

    @TableField(exist = false)
    private static final long serialVersionUID = 130234567891L;
}