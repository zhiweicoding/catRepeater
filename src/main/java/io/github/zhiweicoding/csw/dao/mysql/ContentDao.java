package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.ContentBean;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhiwei
 * @description 针对表【t_content(内容表)】的数据库操作Mapper
 * @createDate 2022-03-20 15:41:26
 */
@Component
public interface ContentDao extends BaseMapper<ContentBean> {

    @Select("select  tm.menu_id as 'menuId',tb.book_id as 'bookId', " +
            "tm.menu_name as 'menuName'," +
            "concat(tb.book_name,'·',tb.book_year) as 'bookName'," +
            "left(t.org_msg,80)  as 'orgMsg' " +
            "from t_content t,t_menu tm,t_book tb where 1=1 and tb.factory_id=#{fId} " +
            "and t.menu_id=tm.menu_id and tb.book_id=tm.book_id " +
            "and (t.org_msg like concat('%',#{msg},'%') or tm.menu_name like concat('%',#{msg},'%')) " +
            "and t.is_delete=0 and tm.is_delete=0 and tb.is_delete=0 order by tm.menu_score desc")
    List<Map<String, Object>> qLike(@Param("fId") int fId, @Param("msg") String msg);

    @MapKey("contentId")
    List<Map<String, Object>> qSearch(@Param("fId") int fId,
                                      @Param("msg") String msg,
                                      @Param("title") String title,
                                      @Param("author") String author,
                                      @Param("start") int start,
                                      @Param("end") int end);
}




