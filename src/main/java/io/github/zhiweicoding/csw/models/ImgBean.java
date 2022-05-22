package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图片库
 *
 * @TableName t_img
 */
@TableName(value = "t_img")
@Data
@Accessors(chain = true)
public class ImgBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "img_id", type = IdType.AUTO)
    private Integer imgId;

    /**
     * 区分那个部分，不同地区，显示不同书籍
     */
    @TableField(value = "factory_id")
    private Integer factoryId;

    /**
     *
     */
    @TableField(value = "book_id")
    private Integer bookId;

    /**
     *
     */
    @TableField(value = "menu_id")
    private String  menuId;

    @TableField(exist = false)
    private String menuName;

    /**
     * 图片描述
     */
    @TableField(value = "img_tag")
    private String imgTag;

    /**
     * 图片信息,html
     */
    @TableField(value = "img_msg")
    private String imgMsg;

    /**
     * 原地址，url
     */
    @TableField(value = "org_url")
    private String orgUrl;

    @TableField(exist = false)
    private int scoreImg;
    @TableField(exist = false)
    private static final long serialVersionUID = 1234567894L;
}