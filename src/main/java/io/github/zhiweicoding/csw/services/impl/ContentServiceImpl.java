package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.constants.RedisConstants;
import io.github.zhiweicoding.csw.dao.mysql.BookDao;
import io.github.zhiweicoding.csw.dao.mysql.ContentDao;
import io.github.zhiweicoding.csw.dao.mysql.ImgDao;
import io.github.zhiweicoding.csw.dao.mysql.MenuDao;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.models.ContentBean;
import io.github.zhiweicoding.csw.models.ImgBean;
import io.github.zhiweicoding.csw.models.MenuBean;
import io.github.zhiweicoding.csw.services.ContentService;
import io.github.zhiweicoding.csw.support.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhiwei
 * @description 针对表【t_content(内容表)】的数据库操作Service实现
 * @createDate 2022-03-20 15:41:26
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentDao, ContentBean> implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ImgDao imgDao;

    @Autowired
    private RedisSupport redisSupport;

    /**
     * 小程序 通过mId 获取小程序内容
     *
     * @param mId
     * @return
     */
    @Override
    public Map<String, Object> byId(String mId, int bId, int fId) {
        Map<String, Object> resultDict = new HashMap<>();

        //redis book
        List<BookBean> bookBeans;
        String formatKey = String.format(RedisConstants.book_key, bId);
        if (redisSupport.exists(formatKey)) {
            bookBeans = (List<BookBean>) redisSupport.get(formatKey);
        } else {
            bookBeans = bookDao.selectList(Wrappers.<BookBean>lambdaQuery()
                    .eq(BookBean::getFactoryId, fId)
                    .eq(BookBean::getIsDelete, 0));
            redisSupport.set(formatKey, bookBeans, 15L, TimeUnit.MINUTES);
        }

        //redis menu
        List<MenuBean> menuBeans;
        String formatMenuKey = String.format(RedisConstants.book_menu_key, bId);
        if (redisSupport.exists(formatMenuKey)) {
            menuBeans = (List<MenuBean>) redisSupport.get(formatMenuKey);
        } else {
            menuBeans = menuDao.selectList(Wrappers.<MenuBean>lambdaQuery()
                    .eq(MenuBean::getBookId, bId)
                    .eq(MenuBean::getIsDelete, 0));
            redisSupport.set(formatMenuKey, menuBeans, 15L, TimeUnit.MINUTES);
        }

        Optional<BookBean> firstB = bookBeans.stream().filter(b -> b.getBookId().equals(bId)).findFirst();
        if (firstB.isPresent()) {
            BookBean bookBean = firstB.get();
            bookBean.setBookName(bookBean.getBookName().replace("天津市", ""));
            resultDict.put("book", bookBean);
        }

        Optional<MenuBean> firstM = menuBeans.stream().filter(b -> b.getMenuId().equals(mId)).findFirst();
        if (firstM.isPresent()) {
            MenuBean menuBean = firstM.get();
            resultDict.put("menu", menuBean);

            String menuIdChain = menuBean.getMenuIdChain();
            List<MenuBean> menuNameArray = new ArrayList<>();
            firstB.ifPresent(bookBean -> {
                MenuBean yearBean = new MenuBean();
                yearBean.setMenuName(bookBean.getBookYear());
                yearBean.setMenuId("-999");
                menuNameArray.add(yearBean);
            });
            if (menuIdChain.contains(",")) {
                for (String item : menuIdChain.split(",")) {
                    for (MenuBean bean : menuBeans) {
                        String tempMId = bean.getMenuId();
                        if (tempMId.equals(item)) {
                            menuNameArray.add(bean);
                        }
                    }
                }
                resultDict.put("menuNameArray", menuNameArray);
            } else {
                menuNameArray.add(menuBean);
                resultDict.put("menuNameArray", menuNameArray);
            }

            Integer menuGrade = menuBean.getMenuGrade();
            List<MenuBean> brotherMenus = new ArrayList<>();
            if (menuGrade == 0) {
                brotherMenus.add(menuBean);
            } else {
                brotherMenus = menuDao.selectList(Wrappers.<MenuBean>lambdaQuery()
                        .eq(MenuBean::getBookId, bId)
                        .eq(MenuBean::getMenuGrade, menuGrade)
                        .eq(MenuBean::getMenuFather, menuBean.getMenuFather())
                        .eq(MenuBean::getIsDelete, 0)
                        .eq(MenuBean::getMenuSon, -1)
                        .orderByDesc(MenuBean::getMenuScore));
            }
            resultDict.put("broMenus", brotherMenus);
        }

        resultDict.put("books", bookBeans);

        LambdaQueryWrapper<ContentBean> eq = Wrappers.<ContentBean>lambdaQuery()
                .eq(ContentBean::getMenuId, mId)
                .eq(ContentBean::getIsDelete, 0);
        List<ImgBean> imgBeans = imgDao.selectList(Wrappers.<ImgBean>lambdaQuery()
                .eq(ImgBean::getMenuId, mId)
                .eq(ImgBean::getIsDelete, 0));
        if (contentDao.exists(eq)) {
            ContentBean contentBean = contentDao.selectList(eq).get(0);
            if (imgBeans != null && imgBeans.size() > 0) {
                StringBuilder sb = new StringBuilder();
//                String contentMsg = contentBean.getContentMsg();
//                sb.append(contentMsg);

//                for (ImgBean imgBean : imgBeans) {
//                    if (StringUtils.hasText(imgBean.getImgMsg())) {
//                        sb.append(imgBean.getImgMsg()).append(System.lineSeparator());
//                        sb.append("<p class=\"imgTagPart\">").append(imgBean.getImgTag()).append("</p>").append(System.lineSeparator());
//                    }
//                }
                contentBean.setImages(imgBeans);
//                contentBean.setContentMsg(sb.toString());
            } else {
                contentBean.setImages(new ArrayList<>());
            }
            resultDict.put("cBean", contentBean);
        } else {
            ContentBean contentBean = new ContentBean();
            if (imgBeans != null && imgBeans.size() > 0) {
                contentBean.setContentMsg("");
                contentBean.setFactoryId(fId);
                contentBean.setOrgMsg("");
                contentBean.setIsContainImg(0);
                String imgAddStr = imgBeans.stream().map(b -> String.valueOf(b.getImgId())).collect(Collectors.joining(","));
                contentBean.setImgAdd(imgAddStr);
                contentBean.setBookId(bId);
                contentBean.setMenuId(mId);
                contentDao.insert(contentBean);
                StringBuilder sb = new StringBuilder();
                //现在不插入正文中
//                for (ImgBean imgBean : imgBeans) {
//                    if (StringUtils.hasText(imgBean.getImgMsg())) {
//                        sb.append(imgBean.getImgMsg()).append(System.lineSeparator());
//                        sb.append("<p class=\"imgTagPart\">").append(imgBean.getImgTag()).append("</p>").append(System.lineSeparator());
//                    }
//                }
                contentBean.setImages(imgBeans);
                contentBean.setContentMsg(sb.toString());
            } else {
                contentBean.setContentId((int) System.currentTimeMillis());
                contentBean.setContentMsg("");
                contentBean.setBookId(bId);
                contentBean.setMenuId(mId);
                contentBean.setImages(new ArrayList<>());
                contentBean.setIsContainImg(1);
            }
            resultDict.put("cBean", contentBean);
        }
        return resultDict;
    }

    /**
     * 小程序内容简单查询
     *
     * @param msg
     * @return
     */
    @Override
    public List<Map<String, Object>> searchSimple(int fId, String msg) {
        List<Map<String, Object>> maps = contentDao.qLike(fId, msg);
        return distinct(maps);
    }

    /**
     * 小程序内容复杂查询
     *
     * @param title
     * @param msg
     * @param author
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Map<String, Object>> searchComplex(int fId, String title, String msg, String author, int start, int end) {
        List<Map<String, Object>> maps = contentDao.qSearch(fId, changeNull(msg), changeNull(title), changeNull(author), start, end);
        return distinct(maps);
    }

    private String changeNull(String obj) {
        if (obj == null || obj.equals("") || obj.equals("null")) {
            return null;
        }
        return obj;
    }

    private List<Map<String, Object>> distinct(List<Map<String, Object>> maps) {
        List<String> menuIds = maps.stream().map(bean -> String.valueOf(bean.get("menuId"))).distinct().collect(Collectors.toList());
        List<Map<String, Object>> resultArray = new ArrayList<>();
        for (String menuId : menuIds) {
            Optional<Map<String, Object>> optional = maps.stream().filter(bean -> String.valueOf(bean.get("menuId")).equals(menuId)).findFirst();
            optional.ifPresent(resultArray::add);
        }
        return resultArray;
    }
}




