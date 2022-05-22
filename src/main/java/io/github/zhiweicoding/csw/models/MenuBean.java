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
 * 目录表
 *
 * @TableName t_menu
 */
@TableName(value = "t_menu")
@Data
@Accessors(chain = true)
public class MenuBean extends BaseMapperBean implements Serializable {
    /**
     *
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private String menuId;

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
     * 目录名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 根节点是0
     */
    @TableField(value = "menu_grade")
    private Integer menuGrade;

    /**
     * 上级 0 就是最大的等级
     */
    @TableField(value = "menu_father")
    private String menuFather;

    /**
     * 下级 -1 就是最后一个等级
     */
    @TableField(value = "menu_son")
    private Integer menuSon;

    /**
     * 同级排序
     */
    @TableField(value = "menu_score")
    private Integer menuScore;

    /**
     * name,分隔
     */
    @TableField(value = "menu_name_chain")
    private String menuNameChain;

    /**
     * id,分隔
     */
    @TableField(value = "menu_id_chain")
    private String menuIdChain;

    /**
     * 阅读次数
     */
    @TableField(value = "read_num")
    private int readNum;

    @TableField(exist = false)
    private List<ContentBean> contentBeans;

    @TableField(exist = false)
    private List<ImgBean> imgBeans;

    @TableField(exist = false)
    private List<MenuBean> sonMenuArray;
    @TableField(exist = false)
    private boolean containSon;

    @TableField(exist = false)
    private String sonText;

    @TableField(exist = false)
    private List<String> sonArray;

    @TableField(exist = false)
    private static final long serialVersionUID = 1234567895L;
}