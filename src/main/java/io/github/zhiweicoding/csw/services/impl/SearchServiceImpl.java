package io.github.zhiweicoding.csw.services.impl;

import io.github.zhiweicoding.csw.dao.mysql.BookDao;
import io.github.zhiweicoding.csw.dao.mysql.ContentDao;
import io.github.zhiweicoding.csw.dao.mysql.ImgDao;
import io.github.zhiweicoding.csw.dao.mysql.MenuDao;
import io.github.zhiweicoding.csw.entity.EsQueryResponse;
import io.github.zhiweicoding.csw.services.SearchService;
import io.github.zhiweicoding.csw.support.RedisSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhiwei
 * @description 针对表【t_content(内容表)】的数据库操作Service实现
 * @createDate 2022-03-20 15:41:26
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

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
     * 小程序内容简单查询
     *
     * @param msg
     * @return
     */
    @Override
    public List<EsQueryResponse.SourceBean> searchSimple(int fId, String msg) {
        List<EsQueryResponse.SourceBean> ss = new ArrayList<>();
        return ss;
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
    public List<EsQueryResponse.SourceBean> searchComplex(int fId, String title, String msg, String author, int start, int end) {
        List<EsQueryResponse.SourceBean> ss = new ArrayList<>();
        return ss;
    }


}




