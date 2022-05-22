package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author zhiwei
 */
@Component
public interface MemberPointsDao extends BaseMapper<MemberPointsBean> {

    @Insert("INSERT INTO t_member_points  " +
            " (`points_id`, `user_id`, `total_money`, `leave_money`, `level_id`, `is_delete`,  " +
            " `create_time`, `pay_type`, `receipt_money`, `which_radio`, `bank_money`, `need_notice`, `ext_msg`,`modify_time`)  " +
            "VALUES (REPLACE(UUID(),'-',''), #{userId}, #{have}, #{have}, " +
            " '63fba47a69c646498f8cebcad2e05258,be43ed7be5c74b69967bdac428735c36,be43ed7be5c74b69967bdac428735c39', " +
            " 0, NOW(), 'ALL', '0.0', #{radio}, #{leave}, 0, NULL,now())")
    void insertBlank(@Param("userId") String userId,
                     @Param("leave") double leave,
                     @Param("have") double have,
                     @Param("radio") int radio
    );
}




