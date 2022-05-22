package io.github.zhiweicoding.csw.dao.mysql;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.models.BookBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Created by zhiwei on 2022/3/21.
 */
@SpringBootTest
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public void queryTest() {
        LambdaQueryWrapper<BookBean> wrapper = Wrappers.<BookBean>lambdaQuery()
                .ge(BookBean::getBookId, 1)
                .orderByDesc(BookBean::getCreateTime);
        List<BookBean> bookBeans = bookDao.selectList(wrapper);
        System.out.println(JSON.toJSONString(bookBeans));
    }

    @Test
    public void insertTest() {
        BookBean bookBean = new BookBean();
        bookBean.setBookAuthor("author");
        bookBean.setBookFrom("from");
        bookBean.setBookImg("https://lg-qrm18qcm-1255940368.cos.ap-shanghai.myqcloud.com/miniProgram/1569.jpeg");
        bookBean.setBookYear("year");
        bookBean.setFactoryId(1);
        bookBean.setReadNum(123L);
        bookBean.setBookName("name");

        bookDao.insert(bookBean);
    }

    @Test
    public void modTest() {
        BookBean bookBean = new BookBean();
        bookBean.setBookFrom("1");
        LambdaUpdateWrapper<BookBean> wrapper = Wrappers.<BookBean>lambdaUpdate().eq(BookBean::getBookId, 1).set(BookBean::getBookFrom, "2");
        bookDao.update(null, wrapper);
    }

    @Test
    public void testMulti() {
        long st = System.currentTimeMillis();
        List<BookBean> bookBeans = bookDao.qFWithMenuById("2");
        long et = System.currentTimeMillis();
        System.out.println(et - st + "ms");
        System.out.println(JSON.toJSONString(bookBeans));
    }
}