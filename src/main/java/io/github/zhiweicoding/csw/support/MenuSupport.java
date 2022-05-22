package io.github.zhiweicoding.csw.support;

import io.github.zhiweicoding.csw.models.MenuBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Created by zhiwei on 2022/3/27.
 */
public class MenuSupport implements Support<List<MenuBean>> {

    private List<MenuBean> tempMenuArray = new ArrayList<>();

    /**
     * 初始化list 按照 grade father- father - son - grade son
     *
     * @param menuBeans
     */
    @Override
    public void init(List<MenuBean> menuBeans) {
        List<MenuBean> rootMenuArray = menuBeans
                .stream()
                .filter(bean -> bean.getMenuGrade() == 0)
                .sorted((o1, o2) -> o2.getMenuScore() - o1.getMenuScore())
                .collect(Collectors.toList());
        for (MenuBean menuBean : rootMenuArray) {
            String rootMenuId = menuBean.getMenuId();
            Integer menuSon = menuBean.getMenuSon();
            Integer menuGrade = menuBean.getMenuGrade();
            if (menuSon != -1) {
                menuBean.setContainSon(true);
                List<MenuBean> sonMenuArray = menuBeans
                        .stream()
                        .filter(bean -> bean.getMenuFather().equals(rootMenuId))
                        .sorted((o1, o2) -> o2.getMenuScore() - o1.getMenuScore())
                        .collect(Collectors.toList());
                menuBean.setSonMenuArray(sonMenuArray);
                putInto(menuBeans, sonMenuArray, menuGrade + 1);
            } else {
                menuBean.setContainSon(false);
                menuBean.setSonMenuArray(null);
            }
        }
        tempMenuArray = rootMenuArray;
    }

    public void putInto(List<MenuBean> allArray, List<MenuBean> menuBeans, int grade) {
        for (MenuBean mb : menuBeans) {
            String rootMenuId = mb.getMenuId();
            Integer menuSon = mb.getMenuSon();
            Integer menuGrade = mb.getMenuGrade();
            if (grade == menuGrade) {
                if (menuSon != -1) {
                    mb.setContainSon(true);
                    List<MenuBean> sonMenuArray = allArray
                            .stream()
                            .filter(bean -> bean.getMenuFather().equals(rootMenuId))
                            .sorted((o1, o2) -> o2.getMenuScore() - o1.getMenuScore())
                            .collect(Collectors.toList());
                    mb.setSonMenuArray(sonMenuArray);
                    putInto(allArray, sonMenuArray, menuGrade + 1);
                } else {
                    mb.setContainSon(false);
                    mb.setSonMenuArray(null);
                }
            }
        }
    }

    /**
     * 获取后台章节排序的list
     *
     * @return
     */
    public List<MenuBean> getBackArray() {
        List<MenuBean> ra = new ArrayList<>();
        addBackArraySon(ra, tempMenuArray);
        return ra;
    }

    public void addBackArraySon(List<MenuBean> ra, List<MenuBean> sonRa) {
        for (MenuBean menuBean : sonRa) {
            ra.add(menuBean);
            if (menuBean.isContainSon()) {
                addBackArraySon(ra, menuBean.getSonMenuArray());
            }
        }
    }

    /**
     * 开始操作
     * @return
     */
    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean clear() {
        tempMenuArray.clear();
        return true;
    }
}
