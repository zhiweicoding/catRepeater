package io.github.zhiweicoding.csw.dao.mysql;

import io.github.zhiweicoding.csw.models.ImgBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by zhiwei on 2022/4/18.
 */
@SpringBootTest
public class ImgDaoTest {

    @Autowired
    private ImgDao imgDao;

    @Test
    void qLike() {
        List<String> ms=new ArrayList<>();
        ms.add("红");
        ms.add("十");

        List<ImgBean> maps = imgDao.qLike(3, ms);
        System.out.println(maps);
    }

    @Test
    void qSearch() {
        List<String> ms=new ArrayList<>();
        ms.add("红");
        ms.add("十");

        List<ImgBean> maps = imgDao.qSearch(3, ms, null, null, 2020, 2022);
        System.out.println(maps);
    }
}