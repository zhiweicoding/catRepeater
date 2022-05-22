package io.github.zhiweicoding.csw.dao.mysql;

import io.github.zhiweicoding.csw.models.FactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Created by zhiwei on 2022/3/20.
 */
@SpringBootTest
@Slf4j
public class FactoryDaoTest {

    @Resource
    private FactoryDao factoryDao;

    @Test
    public void insertTest(){
        FactoryBean factoryBean = factoryDao.selectById(1);
        System.out.println(factoryBean);
    }
}