package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表
 *
 * @TableName t_good
 */
@TableName(value = "t_good")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodBean extends BaseMapperBean implements Serializable {

    /**
     *
     */
    @TableId(value = "good_id", type = IdType.INPUT)
    private String goodId;

    /**
     *
     */
    @TableField(value = "type_id")
    private String typeId;

    /**
     *
     */
    @TableField(value = "good_title")
    private String goodTitle;

    /**
     *
     */
    @TableField(value = "good_brief")
    private String goodBrief;

    /**
     *
     */
    @TableField(value = "retail_price")
    private BigDecimal retailPrice;

    /**
     *
     */
    @TableField(value = "org_price")
    private BigDecimal orgPrice;

    /**
     *
     */
    @TableField(value = "member_price")
    private BigDecimal memberPrice;

    /**
     *
     */
    @TableField(value = "front_money")
    private BigDecimal frontMoney;

    /**
     *
     */
    @TableField(value = "second_money")
    private BigDecimal secondMoney;

    /**
     *
     */
    @TableField(value = "ipad_money")
    private BigDecimal ipadMoney;

    /**
     *
     */
    @TableField(value = "ling_money")
    private BigDecimal lingMoney;

    /**
     * 进货价
     */
    @TableField(value = "get_money")
    private BigDecimal getMoney;

    /**
     *
     */
    @TableField(value = "trans_free")
    private BigDecimal transFree;

    /**
     *
     */
    @TableField(value = "card_money")
    private BigDecimal cardMoney;

    /**
     * 额外费用
     */
    @TableField(value = "ext_money")
    private BigDecimal extMoney;

    /**
     *
     */
    @TableField(value = "symbol_id")
    private String symbolId;

    /**
     *
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     *
     */
    @TableField(value = "good_number")
    private Integer goodNumber;

    /**
     *
     */
    @TableField(value = "like_num")
    private Integer likeNum;

    /**
     *
     */
    @TableField(value = "list_pic_url")
    private String listPicUrl;

    /**
     *
     */
    @TableField(value = "video_url_vertical")
    private String videoUrlVertical;

    /**
     *
     */
    @TableField(value = "img_desc_urls")
    private String imgDescUrls;

    /**
     *
     */
    @TableField(value = "tencent_video")
    private String tencentVideo;

    /**
     *
     */
    @TableField(value = "ali_video")
    private String aliVideo;

    /**
     *
     */
    @TableField(value = "width_video")
    private Double widthVideo;

    /**
     *
     */
    @TableField(value = "height_video")
    private Double heightVideo;

    /**
     *
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     *
     */
    @TableField(value = "shop_name")
    private String shopName;

    /**
     *
     */
    @TableField(value = "pic_urls")
    private String picUrls;

    /**
     *
     */
    @TableField(value = "item_desc_urls")
    private String itemDescUrls;

    /**
     *
     */
    @TableField(value = "sell_num")
    private Integer sellNum;

    /**
     *
     */
    @TableField(value = "star_rate")
    private String starRate;

    /**
     *
     */
    @TableField(value = "star_num")
    private Integer starNum;

    /**
     *
     */
    @TableField(value = "comment_num")
    private Integer commentNum;

    /**
     * cat product member
     */
    @TableField(value = "good_type")
    private String goodType;

    /**
     *
     */
    @TableField(value = "weapp_shop_name")
    private String weappShopName;

    /**
     *
     */
    @TableField(value = "org_desc")
    private String orgDesc;

    /**
     *
     */
    @TableField(value = "video_secret")
    private String videoSecret;

    /**
     *
     */
    @TableField(value = "pet_desc")
    private String petDesc;

    /**
     * 0 正常
     * 1 一步全付款
     */
    @TableField(value = "one_step")
    private Integer oneStep;

    /**
     *
     */
    @TableField(value = "tag_list")
    private String tagList;

    /**
     * 0 试养 1 正式购买 2 领养
     */
    @TableField(value = "can_try_buy")
    private Integer canTryBuy;

    /**
     *
     */
    @TableField(value = "bargain_price")
    private Integer bargainPrice;

    /**
     * 0 不是半价 1 是半价
     */
    @TableField(value = "half_price")
    private Integer halfPrice;

    /**
     *
     */
    @TableField(value = "yimiao")
    private Integer yimiao;

    /**
     *
     */
    @TableField(value = "yimiaoNum")
    private Integer yimiaoNum;

    /**
     *
     */
    @TableField(value = "quchong")
    private Integer quchong;

    /**
     *
     */
    @TableField(value = "xieyi")
    private Integer xieyi;

    /**
     *
     */
    @TableField(value = "is_new")
    private Integer isNew;

    /**
     *
     */
    @TableField(value = "is_chosen")
    private Integer isChosen;

    /**
     * 每日列表售罄标示
     */
    @TableField(value = "is_cheap")
    private Integer isCheap;

    /**
     *
     */
    @TableField(value = "is_free")
    private Integer isFree;

    /**
     * 视频 横竖  0 默认竖屏 1横屏图片 2横屏视频
     */
    @TableField(value = "scale")
    private Integer scale;

    /**
     * 获取每个缩率图指纹
     */
    @TableField(value = "finger_md")
    private String fingerMd;

    /**
     *
     */
    @TableField(value = "click_num")
    private Integer clickNum;

    /**
     *
     */
    @TableField(value = "is_parse")
    private Integer isParse;

    /**
     *
     */
    @TableField(value = "config_id")
    private Integer configId;

    /**
     *
     */
    @TableField(value = "get_id")
    private String getId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}