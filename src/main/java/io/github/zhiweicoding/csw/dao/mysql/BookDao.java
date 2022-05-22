package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.BookBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhiwei
 * @description 针对表【t_book(书籍表)】的数据库操作Mapper
 * @createDate 2022-03-20 15:41:26
 */
@Component
public interface BookDao extends BaseMapper<BookBean> {
//@ResultMap("mybatis-plus_Person")

    @Select("select t.*," +
            "tm.menu_id, tm.factory_id, tm.book_id, tm.menu_name, tm.menu_grade, tm.menu_father, tm.menu_son, tm.menu_name_chain, tm.menu_id_chain," +
            "ti.img_id, ti.img_tag, ti.img_msg, ti.org_url, " +
            "tc.content_id, tc.content_msg, tc.org_msg, tc.is_contain_img, tc.img_add " +
            "from t_book t inner join t_factory tf on tf.factory_id=t.factory_id and tf.is_delete=0 and tf.is_use=0 " +
            "left join t_menu tm on t.book_id=tm.book_id " +
            "left join t_img ti on ti.book_id=t.book_id " +
            "left join t_content tc on tc.book_id=t.book_id " +
            "where 1=1 and t.book_id=#{bookId} " +
            "and t.is_delete=0 and tm.is_delete=0 and ti.is_delete=0 and tc.is_delete=0 ")
    @ResultMap("bookBean")
    List<BookBean> qFWithMenuById(@Param("bookId") String bookId);
}




