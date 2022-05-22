package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.constants.RedisConstants;
import io.github.zhiweicoding.csw.dao.mysql.*;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.models.MenuBean;
import io.github.zhiweicoding.csw.services.MenuService;
import io.github.zhiweicoding.csw.support.RedisSupport;
import io.github.zhiweicoding.csw.utils.GeneratorUtils;
import io.github.zhiweicoding.csw.dao.mysql.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhiwei
 * @description 针对表【t_menu(目录表)】的数据库操作Service实现
 * @createDate 2022-03-20 15:41:26
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuBean> implements MenuService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ImgDao imgDao;

    @Autowired
    private FactoryDao factoryDao;

    @Autowired
    private RedisSupport redisSupport;

    /**
     * 后台接口
     *
     * @param menuName
     * @param bookId
     * @param menuScore
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(String menuName, String bookId, String menuScore) {
        try {
            BookBean bookBean = bookDao.selectById(bookId);
            String commonId = GeneratorUtils.getCommonId();
            MenuBean menuBean = new MenuBean();
            menuBean.setMenuId(commonId);
            menuBean.setBookId(bookBean.getBookId());
            menuBean.setFactoryId(bookBean.getFactoryId());
            menuBean.setMenuName(menuName);
            menuBean.setMenuGrade(0);
            menuBean.setMenuFather("0");
            menuBean.setMenuSon(-1);
            menuBean.setMenuScore(Integer.valueOf(menuScore));
            menuBean.setMenuNameChain(menuName);
            menuBean.setMenuIdChain(commonId);
            menuDao.insert(menuBean);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 后台接口
     *
     * @param menuName
     * @param bookId
     * @param menuScore
     * @param menuId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSon(String menuName, String bookId, String menuScore, String menuId) {
        try {
            MenuBean fatherMenuBean = menuDao.selectById(menuId);
            BookBean bookBean = bookDao.selectById(bookId);
            MenuBean menuBean = new MenuBean();
            String commonId = GeneratorUtils.getCommonId();
            menuBean.setMenuId(commonId);
            menuBean.setBookId(bookBean.getBookId());
            menuBean.setFactoryId(bookBean.getFactoryId());
            menuBean.setMenuName(menuName);
            menuBean.setMenuGrade(fatherMenuBean.getMenuGrade() + 1);
            menuBean.setMenuFather(fatherMenuBean.getMenuId());
            menuBean.setMenuSon(-1);
            menuBean.setMenuScore(Integer.valueOf(menuScore));
            menuBean.setMenuNameChain(fatherMenuBean.getMenuNameChain() + "," + menuName);
            menuBean.setMenuIdChain(fatherMenuBean.getMenuIdChain() + "," + commonId);
            menuDao.insert(menuBean);

            menuDao.update(null, Wrappers.<MenuBean>lambdaUpdate()
                    .set(MenuBean::getMenuSon, Math.abs(fatherMenuBean.getMenuScore()))
                    .eq(MenuBean::getMenuId, fatherMenuBean.getMenuId()));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 后台接口，根据menu id 删除menu和内容，img
     *
     * @param menuId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllById(String menuId) {
        List<String> ms = new ArrayList<>();
        ms.add(menuId);
        MenuBean menuBean = menuDao.selectById(menuId);
        String menuIdChain = menuBean.getMenuIdChain();
        if (menuIdChain != null && menuIdChain.contains(menuId + ",")) {
            String[] split = menuIdChain.split(menuId + ",");
            if (split.length >= 1) {
                String[] sons = split[1].trim().split(",");
                ms.addAll(Arrays.asList(sons));
            }
        }

        if (menuIdChain != null && menuIdChain.contains("," + menuId)) {
            String[] fatherSplit = menuIdChain.split("," + menuId);
            String father = fatherSplit[0];
            LambdaUpdateWrapper<MenuBean> notFinishWrapper = Wrappers.<MenuBean>lambdaUpdate()
                    .set(MenuBean::getMenuSon, -1);
            LambdaQueryWrapper<MenuBean> qWrapper = Wrappers.<MenuBean>lambdaQuery()
                    .eq(MenuBean::getIsDelete, 0)
                    .orderByDesc(MenuBean::getMenuScore);
            if (father.contains(",")) {
                String[] partFather = father.split(",");
                String realId = partFather[partFather.length - 1];

                qWrapper.eq(MenuBean::getMenuFather, realId);
                long existNum = menuDao.selectCount(qWrapper);
                if (existNum <= 0) {
                    menuDao.update(null, notFinishWrapper.eq(MenuBean::getMenuId, realId));
                }
            } else {
                qWrapper.eq(MenuBean::getMenuFather, father);
                long existNum = menuDao.selectCount(qWrapper);
                if (existNum <= 0) {
                    menuDao.update(null, notFinishWrapper.eq(MenuBean::getMenuId, father));
                }

            }
        }
        menuDao.deleteBatchIds(ms);
    }

    /**
     * 小程序接口 目录页
     *
     * @param bId
     * @return
     */
    @Override
    public Map<String, Object> byId(int bId, int fId) {
        List<BookBean> bookBeans;
        String formatKey = String.format(RedisConstants.book_key, bId);
        if (redisSupport.exists(formatKey)) {
            bookBeans = (List<BookBean>) redisSupport.get(formatKey);
        } else {
            bookBeans = bookDao.selectList(Wrappers.<BookBean>lambdaQuery()
                    .eq(BookBean::getFactoryId, fId)
                    .eq(BookBean::getIsDelete, 0));
            redisSupport.set(formatKey, bookBeans, 1L, TimeUnit.HOURS);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("books", bookBeans);

        List<MenuBean> menuBeans = menuDao.selectList(Wrappers.<MenuBean>lambdaQuery()
                .eq(MenuBean::getBookId, bId)
                .eq(MenuBean::getMenuGrade, 0)
                .eq(MenuBean::getIsDelete, 0)
                .orderByDesc(MenuBean::getMenuScore));

        resultMap.put("menus", menuBeans);
        return resultMap;
    }

    /**
     * 小程序接口 目录页 获取子页面
     *
     * @param mId
     * @param bId
     * @param fId
     * @return
     */
    @Override
    public Map<String, Object> getSonByFatherId(String mId, int bId, int fId) {
        List<BookBean> bookBeans;
        String formatKey = String.format(RedisConstants.book_key, bId);
        if (redisSupport.exists(formatKey)) {
            bookBeans = (List<BookBean>) redisSupport.get(formatKey);
        } else {
            bookBeans = bookDao.selectList(Wrappers.<BookBean>lambdaQuery()
                    .eq(BookBean::getFactoryId, fId)
                    .eq(BookBean::getIsDelete, 0));
            redisSupport.set(formatKey, bookBeans, 1L, TimeUnit.HOURS);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("books", bookBeans);

        List<MenuBean> menuBeans = menuDao.selectList(Wrappers.<MenuBean>lambdaQuery()
                .eq(MenuBean::getBookId, bId)
                .eq(MenuBean::getMenuFather, mId)
                .eq(MenuBean::getIsDelete, 0)
                .orderByDesc(MenuBean::getMenuScore));

        resultMap.put("menus", menuBeans);
        return resultMap;
    }
}




