package io.github.zhiweicoding.csw.support;

import com.alibaba.fastjson.JSON;
import io.github.zhiweicoding.csw.dao.mysql.ContentDao;
import io.github.zhiweicoding.csw.dao.mysql.MenuDao;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.models.ContentBean;
import io.github.zhiweicoding.csw.models.MenuBean;
import io.github.zhiweicoding.csw.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Created by zhiwei on 2022/3/27.
 */
@Slf4j
@Component
public class WriterSupport implements Support<String> {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private ContentDao contentDao;

    private boolean canWork = false;

    private final List<MenuBean> tempMenuArray = new ArrayList<>();

    private String tempStr = "";

    private BookBean bookBean;
    private int menuScore = 999999999;

    public static final String level1 = "$.";
    public static final String level2 = "$-$.";
    public static final String level3 = "$-$-$.";
    public static final String level4 = "$-$-$-$.";
    public static final String level5 = "$-$-$-$-$.";
    public static final String bold = "#";
    public static final String boldReg = "#[\\S]+#";

    public static final String bold_center = "*";
    public static final String boldCenterReg = "\\*[\\S]+\\*";
    public static final String center = "**";
    public static final String centerReg = "\\*\\*[\\S]+\\*\\*";
    public static final String line_gap = "thisIsAGapWhatNameIsMySpecialLineGap";
    public static final String temp_line_gap = "thisIsAGapWhatNameIsMySpecialLineGapTemp";


    /**
     * 初始化
     * 作者 authorPart <span class="authorPart">（真真）</span>
     * bold bolderPart <span class="bolderPart">生活就像是一盒强颗粒</span>
     * bold_center bolderCenterPart <p class="bolderCenterPart">生活就像是一盒强颗粒</p>
     * center centerPart <p class="bolderCenterPart">生活就像是一盒强颗粒</p>
     *
     * @param s
     */
    @Override
    public void init(String s) {
        List<String> cacheArray = new ArrayList<>();
        int i = 0;
        String firstLine = "";
        for (String item : s.split(System.lineSeparator())) {
            if (StringUtils.hasText(item)) {
                if (i == 0) {
                    firstLine = item;
                } else {
                    Pattern pc = Pattern.compile(centerReg);
                    Matcher mc = pc.matcher(item);
                    while (mc.find()) {
                        String leaveBoldStr = mc.group();
                        item = item.replace(leaveBoldStr, "");
                        leaveBoldStr = leaveBoldStr.replace(center, "");
                        item = "<p class=\"centerPart\">" + leaveBoldStr + "</p>" + item;
                    }

                    Pattern pcb = Pattern.compile(boldCenterReg);
                    Matcher mcb = pcb.matcher(item);
                    while (mcb.find()) {
                        String leaveBoldStr = mcb.group();
                        item = item.replace(leaveBoldStr, "");
                        leaveBoldStr = leaveBoldStr.replace(bold_center, "");
                        item = "<p class=\"bolderCenterPart\">" + leaveBoldStr + "</p>" + item;
                    }

                    Pattern pb = Pattern.compile(boldReg);
                    Matcher mb = pb.matcher(item);
                    while (mb.find()) {
                        String leaveBoldStr = mb.group();
                        item = item.replace(leaveBoldStr, "");
                        leaveBoldStr = leaveBoldStr.replace(bold, "");
                        item = "<span class=\"bolderPart\">" + leaveBoldStr + "</span>" + item;
                    }

                    cacheArray.add(item);
                }

            }
            i++;
        }
        this.tempStr = String.join(line_gap, cacheArray);

        boolean sLevel1 = s.startsWith(level1);
        if (sLevel1) {
            String commonId = GeneratorUtils.getCommonId();
            String menuName = firstLine.replace(level1, "");
            MenuBean menuBean = new MenuBean();
            menuBean.setMenuId(commonId);
            menuBean.setMenuName(menuName.trim());
            menuBean.setFactoryId(bookBean.getFactoryId());
            menuBean.setBookId(bookBean.getBookId());
            menuBean.setMenuGrade(0);
            menuBean.setMenuFather("0");
            menuBean.setMenuSon(-1);
            menuBean.setMenuScore(menuScore);
            menuBean.setMenuNameChain(menuName);
            menuBean.setMenuIdChain(commonId);
            menuBean.setSonText(tempStr.replace(firstLine + line_gap, ""));
            menuBean.setSonArray(cacheArray);

            menuScore--;
            tempMenuArray.add(menuBean);
        }
    }

    /**
     * 开始操作
     *
     * @return
     */
    @Override
    public boolean start() {
        if (bookBean == null) {
            log.error("导入数据前，没有把书籍信息置入，传入数据：{}", tempStr);
            return false;
        } else if (tempMenuArray.size() <= 0) {
            log.error("导入数据,没有找到根节点，传入数据：{}", tempStr);
            return false;
        } else {
            canWork = true;
            if (canWork) {
                MenuBean rootMenu = tempMenuArray.get(0);
                List<MenuBean> level2s = getMenuBeans(rootMenu, level2);
                if (level2s.size() > 0) {
                    rootMenu.setSonMenuArray(level2s);
                    rootMenu.setContainSon(true);
                    for (MenuBean m2 : level2s) {
                        List<MenuBean> level3s = getMenuBeans(m2, level3);
                        if (level3s.size() > 0) {
                            m2.setSonMenuArray(level3s);
                            m2.setContainSon(true);
                            for (MenuBean m3 : level3s) {
                                List<MenuBean> level4s = getMenuBeans(m3, level4);
                                if (level4s.size() > 0) {
                                    m3.setSonMenuArray(level4s);
                                    m3.setContainSon(true);
                                    for (MenuBean m4 : level4s) {
                                        getContent(m4);
                                    }
                                } else {
                                    m3.setSonMenuArray(new ArrayList<>());
                                    m3.setContainSon(false);
                                }
                            }
                        } else {
                            m2.setSonMenuArray(new ArrayList<>());
                            m2.setContainSon(false);
                        }

                    }
                } else {
                    rootMenu.setSonMenuArray(new ArrayList<>());
                    rootMenu.setContainSon(false);
                }

            }
            return true;
        }
    }

    @Transactional
    public void insert() {
        insertLoop(this.tempMenuArray);
        tempMenuArray.clear();
    }

    private void insertLoop(List<MenuBean> mbs) {
        for (MenuBean m : mbs) {
            log.debug("开始插入：{}", m.getMenuId());
            menuDao.insert(m);
            if (m.isContainSon()) {
                insertLoop(m.getSonMenuArray());
            } else {
                List<ContentBean> cs = m.getContentBeans();
                if (cs != null && cs.size() > 0) {
                    ContentBean contentBean = cs.get(0);
                    String orgMsg = contentBean.getOrgMsg();
                    String contentMsg = contentBean.getContentMsg();
                    contentBean.setOrgMsg(orgMsg.replace(level1, "").replace(level2, "").replace(level4, "").replace(level5, "").replace(level3, ""));
                    contentBean.setContentMsg(contentMsg.replace(level1, "").replace(level2, "").replace(level4, "").replace(level5, "").replace(level3, ""));
                    contentDao.insert(contentBean);
                } else {
                    log.debug("insert loop 失败了：{}", JSON.toJSON(m));
                }
            }
        }
    }

    /**
     * 作者 authorPart <span class="authorPart">（真真）</span>
     * bold bolderPart <span class="bolderPart">生活就像是一盒强颗粒</span>
     * bold_center bolderCenterPart <p class="bolderCenterPart">生活就像是一盒强颗粒</p>
     * center centerPart <p class="bolderCenterPart">生活就像是一盒强颗粒</p>
     */
    public void getContent(MenuBean father) {
        String sonText = father.getSonText().replace("$-", "");
        ContentBean contentBean = new ContentBean();
        contentBean.setFactoryId(father.getFactoryId());
        contentBean.setBookId(father.getBookId());
        contentBean.setMenuId(father.getMenuId());
        StringBuilder orgSb = new StringBuilder("<div class=\"div_class\">").append(System.lineSeparator());
        String[] split = sonText.split(line_gap);
        for (String s : split) {
            if (s != null && !s.trim().equals("")) {
                if (s.startsWith("（") && s.endsWith("）")) {
                    orgSb.append("<p class=\"authorPart\">").append(System.lineSeparator()).append(s).append(System.lineSeparator()).append("</p>").append(System.lineSeparator());
                } else if (!s.contains("<p")) {
                    orgSb.append("<p class=\"segPart\">").append(System.lineSeparator());
                    orgSb.append(s).append(System.lineSeparator());
                    orgSb.append("</p>").append(System.lineSeparator());
                } else {
                    orgSb.append(s).append(System.lineSeparator());
                }
            }
        }
        orgSb.append("</div>").append(System.lineSeparator());
        contentBean.setContentMsg(orgSb.toString());
        sonText = sonText.replace(line_gap, System.lineSeparator())
                .replace("<p class=\"centerPart\">", "")
                .replace("<p class=\"bolderCenterPart\">", "")
                .replace("<span class=\"bolderPart\">", "")
                .replace("</p>", "")
                .replace("</span>", "");
        contentBean.setOrgMsg(sonText);
        contentBean.setIsContainImg(1);
        father.setContainSon(false);
        father.setContentBeans(Collections.singletonList(contentBean));
    }

    private List<MenuBean> getMenuBeans(MenuBean fatherMenu, String levelStr) {
        List<MenuBean> levels = new ArrayList<>();
        String sonText = fatherMenu.getSonText();
        List<String> sonArray = fatherMenu.getSonArray();
        if (sonText.contains(levelStr)) {
            List<Integer> indexArray = new ArrayList<>();
            int index = 0;
            for (String item : sonArray) {
                boolean sLevel = item.startsWith(levelStr);
                if (sLevel) {
                    String commonId = GeneratorUtils.getCommonId();
                    String menuName = item.replace(levelStr, "");
                    MenuBean menuBean = new MenuBean();
                    menuBean.setMenuId(commonId);
                    menuBean.setMenuName(menuName.trim());
                    menuBean.setFactoryId(bookBean.getFactoryId());
                    menuBean.setBookId(bookBean.getBookId());
                    menuBean.setMenuGrade(fatherMenu.getMenuGrade() + 1);
                    menuBean.setMenuFather(fatherMenu.getMenuId());
                    menuBean.setMenuSon(-1);
                    menuBean.setMenuScore(menuScore);
                    menuBean.setMenuNameChain(fatherMenu.getMenuNameChain() + "," + menuName);
                    menuBean.setMenuIdChain(fatherMenu.getMenuIdChain() + "," + commonId);
                    menuScore--;
                    levels.add(menuBean);
                    fatherMenu.setMenuSon(fatherMenu.getMenuSon() + 1);
                    indexArray.add(index);

                }
                index++;
            }

            if (indexArray.size() > 0) {
                for (int i = 0; i < indexArray.size(); i++) {
                    MenuBean thisBean = levels.get(i);
                    List<String> sub;
                    if (i + 1 == indexArray.size()) {
                        sub = sonArray.subList(indexArray.get(i), sonArray.size() - 1);
                    } else {
                        sub = sonArray.subList(indexArray.get(i), indexArray.get(i + 1));
                    }

                    thisBean.setSonArray(sub);
                    thisBean.setSonText(String.join(line_gap, sub));
                }
            }
//            for (int i = 0; i < levels.size(); i++) {
//                String gapText;
//                if (i == levels.size() - 1) {
//                    //如果只有一个子节点
//                    MenuBean before = levels.get(i);
//                    gapText = SelfUtil.processTitle(sonText, before.getMenuName().trim());
//                    before.setSonText(gapText);
//                } else {
//                    //如果多个子节点
//                    MenuBean before = levels.get(i);
//                    MenuBean thisBean = levels.get(i + 1);
//                    gapText = SelfUtil.processTitle(sonText, before.getMenuName().trim());
//                    gapText = SelfUtil.throwsAfterTitle(gapText, thisBean.getMenuName());
//                    before.setSonText(gapText);
//                }
//
//            }
        } else {
            getContent(fatherMenu);
        }

        return levels;
    }


    @Override
    public boolean stop() {
        canWork = false;
        clear();
        return true;
    }

    @Override
    public boolean clear() {
        tempMenuArray.clear();
        bookBean = null;
        menuScore = 999999999;
        tempStr = "";
        return true;
    }

    public void setBookBean(BookBean bookBean) {
        this.bookBean = bookBean;
    }

    public List<MenuBean> getTempMenuArray() {
        return tempMenuArray;
    }
}
