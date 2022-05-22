package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "用户信息表", description = "用户信息表")
public class UserBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    @TableField(value = "open_id")
    private String openId;
    @TableField(value = "public_open_id")
    private String publicOpenId;
    @TableField(value = "ali_open_id")
    private String aliOpenId;
    @TableField(value = "tt_open_id")
    private String ttOpenId;
    @TableField(value = "baidu_open_id")
    private String baiduOpenId;
    @TableField(value = "is_real_name")
    private Integer isRealName;
    @TableField(value = "member_points_id")
    private String memberPointsId;
    @TableField(value = "member_id")
    private String memberId;
    @TableField(value = "nick_name")
    private String nickName;
    @TableField(value = "user_mobile")
    private String userMobile;
    @TableField(value = "avatar_url")
    private String avatarUrl;
    @TableField(value = "union_id")
    private String unionId;
    @TableField(value = "province")
    private String province;
    @TableField(value = "city")
    private String city;
    @TableField(value = "country")
    private String country;
    @TableField(value = "gender")
    private Integer gender;
    @TableField(value = "language")
    private String language;
    @TableField(value = "type_id")
    private String typeId;
    @TableField(value = "password")
    private String password;
    @TableField(value = "notice_id")
    private String noticeId;
    @TableField(value = "zhifubao_id")
    private String zhifubaoId;
    @TableField(value = "real_name")
    private String realName;
    @TableField(value = "user_type")
    private String userType;
    @TableField(value = "remark_msg")
    private String remarkMsg;
    @TableField(value = "is_member")
    private Integer isMember;
    @TableField(exist = false)
    private static final long serialVersionUID = 1234567896L;
}