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
@TableName(value = "t_member_points")
@Schema(name = "预存会员表", description = "预存会员信息表")
public class MemberPointsBean extends BaseMapperBean implements Serializable {
    @TableId(value = "points_id", type = IdType.INPUT)
    @Schema(name = "主键", description = "主键", example = "1")
    private String pointsId;
    @TableField(value = "user_id")
    @Schema(name = "用户ID", description = "用户ID")
    private String userId;
    @TableField(value = "total_money")
    private Double totalMoney;
    @TableField(value = "leave_money")
    private Double leaveMoney;
    @TableField(value = "level_id")
    private String levelId;
    @TableField(value = "pay_type")
    private String payType;
    @TableField(value = "receipt_money")
    private String receiptMoney;
    @TableField(value = "which_radio")
    private Integer whichRadio;
    @TableField(value = "bank_money")
    private Double bankMoney;
    @TableField(value = "need_notice")
    private Integer needNotice;
    @TableField(value = "ext_msg")
    private String extMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 12345267896L;

}
