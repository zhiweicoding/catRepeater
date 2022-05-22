package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.MenuBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author zhiwei
 * @description 针对表【t_menu(目录表)】的数据库操作Mapper
 * @createDate 2022-03-20 15:41:26
 */
@Component
public interface MenuDao extends BaseMapper<MenuBean> {

    //    @Select("select max(t.menu_id) from t_menu t where 1=1 ")
//    int selectMaxId();
    @Update("update t_menu t set t.read_num=t.read_num+1 where t.menu_id=#{menuId}")
    void readAddOne(@Param("menuId") String menuId);
}




