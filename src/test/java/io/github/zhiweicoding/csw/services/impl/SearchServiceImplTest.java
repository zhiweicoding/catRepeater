package io.github.zhiweicoding.csw.services.impl;

import io.github.zhiweicoding.csw.entity.EsQueryResponse;
import io.github.zhiweicoding.csw.services.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Created by zhiwei on 2022/4/18.
 */
@SpringBootTest
public class SearchServiceImplTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void searchSimple() {
        List<EsQueryResponse.SourceBean> h = searchService.searchSimple(3, "红十字会");
        System.out.println(h.get(0));
    }

    @Test
    public void searchComplex() {
        List<EsQueryResponse.SourceBean> h = searchService.searchComplex(3, "红十字会", "红十", null, 0, 0);
        System.out.println(h.get(0));
    }
}