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
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    @TableId(value = "open_id")
    private String openId;
    @TableId(value = "public_open_id")
    private String publicOpenId;
    @TableId(value = "ali_open_id")
    private String aliOpenId;
    @TableId(value = "tt_open_id")
    private String ttOpenId;
    @TableId(value = "baidu_open_id")
    private String baiduOpenId;
    @TableId(value = "is_real_name")
    private Integer isRealName;
    @TableId(value = "member_points_id")
    private String memberPointsId;
    @TableId(value = "member_id")
    private String memberId;
    @TableId(value = "nick_name")
    private String nickName;
    @TableId(value = "user_mobile")
    private String userMobile;
    @TableId(value = "avatar_url")
    private String avatarUrl;
    @TableId(value = "union_id")
    private String unionId;
    @TableId(value = "province")
    private String province;
    @TableId(value = "city")
    private String city;
    @TableId(value = "country")
    private String country;
    @TableId(value = "gender")
    private Integer gender;
    @TableId(value = "language")
    private String language;
    @TableId(value = "type_id")
    private String typeId;
    @TableId(value = "password")
    private String password;
    @TableId(value = "notice_id")
    private String noticeId;
    @TableId(value = "zhifubao_id")
    private String zhifubaoId;
    @TableId(value = "real_name")
    private String realName;
    @TableId(value = "user_type")
    private String userType;
    @TableId(value = "remark_msg")
    private String remarkMsg;
    @TableId(value = "is_member")
    private Integer isMember;
    @TableField(exist = false)
    private static final long serialVersionUID = 1234567896L;
}