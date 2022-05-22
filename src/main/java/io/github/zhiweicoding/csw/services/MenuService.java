package io.github.zhiweicoding.csw.services;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhiweicoding.csw.models.MenuBean;

import java.util.Map;

/**
 * @author zhiwei
 * @description 针对表【t_menu(目录表)】的数据库操作Service
 * @createDate 2022-03-20 15:41:26
 */
public interface MenuService extends IService<MenuBean> {

    /**
     * 后台接口
     *
     * @param menuName
     * @param bookId
     * @param menuScore
     * @return
     */
    boolean save(String menuName, String bookId, String menuScore);

    /**
     * 后台接口
     *
     * @param menuName
     * @param bookId
     * @param menuScore
     * @param menuId
     * @return
     */
    boolean saveSon(String menuName, String bookId, String menuScore, String menuId);

    /**
     * 后台接口，根据menu id 删除menu和内容，img
     *
     * @param menuId
     */
    void delAllById(String menuId);

    /**
     * 小程序接口 目录页
     *
     * @param bId
     * @return
     */
    Map<String, Object> byId(int bId, int fId);

    /**
     * 小程序接口 目录页 获取子页面
     *
     * @param bId
     * @return
     */
    Map<String, Object> getSonByFatherId(String mId, int bId, int fId);

}
