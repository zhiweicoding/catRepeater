package io.github.zhiweicoding.csw.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Created by zhiwei on 2022/3/27.
 */
@SpringBootTest
public class CosUtilTest {

    @Autowired
    private CosUtil cosUtil;

    @Test
    void delCos() {
//        cosUtil.delCos("https://booklib-1309974988.cos.ap-shanghai.myqcloud.com/folder/image/0.png");
        cosUtil.delCos("folder/image/000000.png");
    }

    @Test
    void download() {
    }

    @Test
    void upFile() {
    }

    @Test
    void testUpFile() {
    }
}