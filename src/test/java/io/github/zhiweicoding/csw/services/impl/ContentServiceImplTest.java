package io.github.zhiweicoding.csw.services.impl;

import io.github.zhiweicoding.csw.dao.mysql.ContentDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhiwei on 2022/4/17.
 */
@SpringBootTest
public class ContentServiceImplTest {

    @Autowired
    private ContentDao contentDao;

    @Test
    public void byId() {
    }

    @Test
    public void searchSimple() {
        List<Map<String, Object>> 红十字会 = contentDao.qLike(3, "红十字会");
        System.out.println(红十字会);
    }

    @Test
    public void searchComplex() {
    }
}