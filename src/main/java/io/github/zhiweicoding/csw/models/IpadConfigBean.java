package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "t_ipad_config")
public class IpadConfigBean extends BaseMapperBean implements Serializable {
    @TableId(value = "config_id", type = IdType.INPUT)
    private String configId;
    @TableField(value = "config_content")
    private String configContent;
    @TableField(value = "config_msg")
    private String configMsg;
    @TableField(value = "config_url")
    private String configUrl;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "other_msg")
    private String otherMsg;
    @TableField(value = "config_type")
    private String configType;

    @TableField(exist = false)
    private static final long serialVersionUID = 123452627896L;

}
