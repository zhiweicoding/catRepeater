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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用户表
 *
 * @TableName t_user
 */
@TableName(value = "t_user")
@com.xpc.easyes.core.anno.TableName("t_user")
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        return new EqualsBuilder().append(userId, userBean.userId).append(openId, userBean.openId).append(publicOpenId, userBean.publicOpenId).append(aliOpenId, userBean.aliOpenId).append(ttOpenId, userBean.ttOpenId).append(baiduOpenId, userBean.baiduOpenId).append(isRealName, userBean.isRealName).append(memberPointsId, userBean.memberPointsId).append(memberId, userBean.memberId).append(nickName, userBean.nickName).append(userMobile, userBean.userMobile).append(avatarUrl, userBean.avatarUrl).append(unionId, userBean.unionId).append(province, userBean.province).append(city, userBean.city).append(country, userBean.country).append(gender, userBean.gender).append(language, userBean.language).append(typeId, userBean.typeId).append(password, userBean.password).append(noticeId, userBean.noticeId).append(zhifubaoId, userBean.zhifubaoId).append(realName, userBean.realName).append(userType, userBean.userType).append(remarkMsg, userBean.remarkMsg).append(isMember, userBean.isMember).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userId).append(openId).append(publicOpenId).append(aliOpenId).append(ttOpenId).append(baiduOpenId).append(isRealName).append(memberPointsId).append(memberId).append(nickName).append(userMobile).append(avatarUrl).append(unionId).append(province).append(city).append(country).append(gender).append(language).append(typeId).append(password).append(noticeId).append(zhifubaoId).append(realName).append(userType).append(remarkMsg).append(isMember).toHashCode();
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId='" + userId + '\'' +
                ", openId='" + openId + '\'' +
                ", publicOpenId='" + publicOpenId + '\'' +
                ", aliOpenId='" + aliOpenId + '\'' +
                ", ttOpenId='" + ttOpenId + '\'' +
                ", baiduOpenId='" + baiduOpenId + '\'' +
                ", isRealName=" + isRealName +
                ", memberPointsId='" + memberPointsId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", unionId='" + unionId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", gender=" + gender +
                ", language='" + language + '\'' +
                ", typeId='" + typeId + '\'' +
                ", password='" + password + '\'' +
                ", noticeId='" + noticeId + '\'' +
                ", zhifubaoId='" + zhifubaoId + '\'' +
                ", realName='" + realName + '\'' +
                ", userType='" + userType + '\'' +
                ", remarkMsg='" + remarkMsg + '\'' +
                ", isMember=" + isMember +
                '}';
    }

    public UserBean() {
    }

    public UserBean(String userId, String openId, String publicOpenId, String aliOpenId, String ttOpenId, String baiduOpenId, Integer isRealName, String memberPointsId, String memberId, String nickName, String userMobile, String avatarUrl, String unionId, String province, String city, String country, Integer gender, String language, String typeId, String password, String noticeId, String zhifubaoId, String realName, String userType, String remarkMsg, Integer isMember) {
        this.userId = userId;
        this.openId = openId;
        this.publicOpenId = publicOpenId;
        this.aliOpenId = aliOpenId;
        this.ttOpenId = ttOpenId;
        this.baiduOpenId = baiduOpenId;
        this.isRealName = isRealName;
        this.memberPointsId = memberPointsId;
        this.memberId = memberId;
        this.nickName = nickName;
        this.userMobile = userMobile;
        this.avatarUrl = avatarUrl;
        this.unionId = unionId;
        this.province = province;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.language = language;
        this.typeId = typeId;
        this.password = password;
        this.noticeId = noticeId;
        this.zhifubaoId = zhifubaoId;
        this.realName = realName;
        this.userType = userType;
        this.remarkMsg = remarkMsg;
        this.isMember = isMember;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPublicOpenId() {
        return publicOpenId;
    }

    public void setPublicOpenId(String publicOpenId) {
        this.publicOpenId = publicOpenId;
    }

    public String getAliOpenId() {
        return aliOpenId;
    }

    public void setAliOpenId(String aliOpenId) {
        this.aliOpenId = aliOpenId;
    }

    public String getTtOpenId() {
        return ttOpenId;
    }

    public void setTtOpenId(String ttOpenId) {
        this.ttOpenId = ttOpenId;
    }

    public String getBaiduOpenId() {
        return baiduOpenId;
    }

    public void setBaiduOpenId(String baiduOpenId) {
        this.baiduOpenId = baiduOpenId;
    }

    public Integer getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(Integer isRealName) {
        this.isRealName = isRealName;
    }

    public String getMemberPointsId() {
        return memberPointsId;
    }

    public void setMemberPointsId(String memberPointsId) {
        this.memberPointsId = memberPointsId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getZhifubaoId() {
        return zhifubaoId;
    }

    public void setZhifubaoId(String zhifubaoId) {
        this.zhifubaoId = zhifubaoId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRemarkMsg() {
        return remarkMsg;
    }

    public void setRemarkMsg(String remarkMsg) {
        this.remarkMsg = remarkMsg;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }
}