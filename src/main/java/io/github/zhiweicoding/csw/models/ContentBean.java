package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 内容表
 * @TableName t_content
 */
@TableName(value ="t_content")
@Data
@Accessors(chain = true)
public class ContentBean extends BaseMapperBean implements Serializable {
    /**
     * 
     */
    @TableId(value = "content_id", type = IdType.AUTO)
    private Integer contentId;

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
    private String menuId;

    /**
     * 那个章节的正文,html格式
     */
    @TableField(value = "content_msg")
    private String contentMsg;

    /**
     * 章节的原文
     */
    @TableField(value = "org_msg")
    private String orgMsg;

    /**
     * 章节作者
     */
    @TableField(value = "content_author")
    private String contentAuthor;

    /**
     * 
     */
    @TableField(value = "is_contain_img")
    private Integer isContainImg;

    /**
     * 图片的id,分隔
     */
    @TableField(value = "img_add")
    private String imgAdd;

    @TableField(exist = false)
    private List<ImgBean> images;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567892L;
}