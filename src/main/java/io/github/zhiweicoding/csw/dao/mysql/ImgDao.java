package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.ImgBean;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhiwei
 * @description 针对表【t_img(图片库)】的数据库操作Mapper
 * @createDate 2022-03-20 15:41:26
 */
@Component
public interface ImgDao extends BaseMapper<ImgBean> {

    @MapKey("imgId")
    List<ImgBean> qLike(@Param("fId") int fId, @Param("ms") List<String> ms);

    @MapKey("imgId")
    List<ImgBean> qSearch(@Param("fId") int fId,
                          @Param("ms") List<String> ms,
                          @Param("title") String title,
                          @Param("author") String author,
                          @Param("start") int start,
                          @Param("end") int end);

}




