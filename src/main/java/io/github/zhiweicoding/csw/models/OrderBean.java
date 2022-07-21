package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@TableName(value = "t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderBean extends BaseMapperBean implements Serializable {
    @TableId(value = "order_id", type = IdType.INPUT)
    private String orderId;
    private int orderStatus;
    private double orderRealPrice;
    private double orderActualPrice;
    private double refundMoney;
    private double frontMoney;
    private double secondMoney;
    private double couponMoney;
    private String couponId;
    private double transMoney;
    private int transType;
    private int transLock;
    private String orderSerial;
    private String goodId;
    private String productListId;
    private String openId;
    private String typeId;
    private String addressId;
    private String addressUserName;
    private String addressFullRegion;
    private String addressName;
    private String addressMobile;
    private int bargainPrice;
    private String extParams;
    private String modifyTimeRecord;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime partPayTime;
    @TableField(exist = false)
    private String partPayTimeStr;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime payTime;
    @TableField(exist = false)
    private String payTimeStr;
    private String prepareId;
    private String userType;
    private String userId;
    private String goodType;
    private String memberId;
    private double memberFloat;
    private String trackNumber;
    private String refundMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 123452672896L;
}
