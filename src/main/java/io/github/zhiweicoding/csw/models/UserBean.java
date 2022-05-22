package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.xpc.easyes.core.enums.FieldType;
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
@com.xpc.easyes.core.anno.TableName("t_user")
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
    @com.xpc.easyes.core.anno.TableId(value = "user_id", type = com.xpc.easyes.core.enums.IdType.CUSTOMIZE)
    private String userId;

    @TableField(value = "open_id")
    @com.xpc.easyes.core.anno.TableField(value = "open_id", fieldType = FieldType.TEXT)
    private String openId;

    @com.xpc.easyes.core.anno.TableField(value = "public_open_id", fieldType = FieldType.TEXT)
    @TableField(value = "public_open_id")
    private String publicOpenId;

    @com.xpc.easyes.core.anno.TableField(value = "ali_open_id", fieldType = FieldType.TEXT)
    @TableField(value = "ali_open_id")
    private String aliOpenId;

    @com.xpc.easyes.core.anno.TableField(value = "tt_open_id", fieldType = FieldType.TEXT)
    @TableField(value = "tt_open_id")
    private String ttOpenId;

    @com.xpc.easyes.core.anno.TableField(value = "baidu_open_id", fieldType = FieldType.TEXT)
    @TableField(value = "baidu_open_id")
    private String baiduOpenId;

    @com.xpc.easyes.core.anno.TableField(value = "is_real_name", fieldType = FieldType.LONG)
    @TableField(value = "is_real_name")
    private Integer isRealName;

    @com.xpc.easyes.core.anno.TableField(value = "member_points_id", fieldType = FieldType.TEXT)
    @TableField(value = "member_points_id")
    private String memberPointsId;

    @com.xpc.easyes.core.anno.TableField(value = "member_id", fieldType = FieldType.TEXT)
    @TableField(value = "member_id")
    private String memberId;

    @com.xpc.easyes.core.anno.TableField(value = "nick_name", fieldType = FieldType.TEXT)
    @TableField(value = "nick_name")
    private String nickName;

    @com.xpc.easyes.core.anno.TableField(value = "user_mobile", fieldType = FieldType.TEXT)
    @TableField(value = "user_mobile")
    private String userMobile;

    @com.xpc.easyes.core.anno.TableField(value = "avatar_url", fieldType = FieldType.TEXT)
    @TableField(value = "avatar_url")
    private String avatarUrl;

    @com.xpc.easyes.core.anno.TableField(value = "union_id", fieldType = FieldType.TEXT)
    @TableField(value = "union_id")
    private String unionId;

    @com.xpc.easyes.core.anno.TableField(value = "province", fieldType = FieldType.TEXT)
    @TableField(value = "province")
    private String province;

    @com.xpc.easyes.core.anno.TableField(value = "city", fieldType = FieldType.TEXT)
    @TableField(value = "city")
    private String city;

    @com.xpc.easyes.core.anno.TableField(value = "country", fieldType = FieldType.TEXT)
    @TableField(value = "country")
    private String country;

    @com.xpc.easyes.core.anno.TableField(value = "gender", fieldType = FieldType.LONG)
    @TableField(value = "gender")
    private Integer gender;

    @com.xpc.easyes.core.anno.TableField(value = "language", fieldType = FieldType.TEXT)
    @TableField(value = "language")
    private String language;

    @com.xpc.easyes.core.anno.TableField(value = "type_id", fieldType = FieldType.TEXT)
    @TableField(value = "type_id")
    private String typeId;

    @com.xpc.easyes.core.anno.TableField(value = "password", fieldType = FieldType.TEXT)
    @TableField(value = "password")
    private String password;

    @com.xpc.easyes.core.anno.TableField(value = "notice_id", fieldType = FieldType.TEXT)
    @TableField(value = "notice_id")
    private String noticeId;

    @com.xpc.easyes.core.anno.TableField(value = "zhifubao_id", fieldType = FieldType.TEXT)
    @TableField(value = "zhifubao_id")
    private String zhifubaoId;

    @com.xpc.easyes.core.anno.TableField(value = "real_name", fieldType = FieldType.TEXT)
    @TableField(value = "real_name")
    private String realName;

    @com.xpc.easyes.core.anno.TableField(value = "user_type", fieldType = FieldType.TEXT)
    @TableField(value = "user_type")
    private String userType;

    @com.xpc.easyes.core.anno.TableField(value = "remark_msg", fieldType = FieldType.TEXT)
    @TableField(value = "remark_msg")
    private String remarkMsg;

    @com.xpc.easyes.core.anno.TableField(value = "is_member", fieldType = FieldType.INTEGER)
    @TableField(value = "is_member")
    private Integer isMember;

    @com.xpc.easyes.core.anno.TableField(exist = false)
    @TableField(exist = false)
    private static final long serialVersionUID = 1234567896L;
}