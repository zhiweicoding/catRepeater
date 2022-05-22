package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.BookDao;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.services.BookService;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei
 * @description 针对表【t_book(书籍表)】的数据库操作Service实现
 * @createDate 2022-03-20 15:41:26
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, BookBean> implements BookService {
}




