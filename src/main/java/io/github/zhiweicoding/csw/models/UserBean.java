package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
@Accessors(chain = true)
public class UserBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value = "password")
    private String password;

    /**
     * 各个第三方对应的open_id
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 微信的联合id
     */
    @TableField(value = "union_id")
    private String unionId;

    /**
     * 这边的联合ID
     */
    @TableField(value = "self_union_id")
    private String selfUnionId;

    /**
     * 第三方平台的昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 国家
     */
    @TableField(value = "country")
    private String country;

    /**
     * 地区
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 性别
     */
    @TableField(value = "sexy")
    private Integer sexy;

    /**
     * 第三方的名称
     */
    @TableField(value = "user_from")
    private Integer userFrom;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567896L;
}