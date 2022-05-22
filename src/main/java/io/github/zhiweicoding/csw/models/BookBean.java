package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 书籍表
 *
 * @TableName t_book
 */
@TableName(value = "t_book", autoResultMap = true)
@Data
@Accessors(chain = true)
public class BookBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    /**
     * 区分那个部分，不同地区，显示不同书籍
     */
    @TableField(value = "factory_id")
    private Integer factoryId;

    /**
     * 书名
     */
    @TableField(value = "book_name")
    private String bookName;

    /**
     * 年份
     */
    @TableField(value = "book_year")
    private String bookYear;

    /**
     * 图书封面
     */
    @TableField(value = "book_img")
    private String bookImg;

    /**
     * 作者
     */
    @TableField(value = "book_author")
    private String bookAuthor;

    /**
     * 阅读量
     */
    @TableField(value = "read_num")
    private Long readNum;

    /**
     * 来自哪里 比如：天津市地方志编修委员会
     */
    @TableField(value = "book_from")
    private String bookFrom;

    @TableField(value = "book_title_name")
    private String bookTitleName;

    @TableField(exist = false)
    private List<MenuBean> menuBeans;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567890L;
}