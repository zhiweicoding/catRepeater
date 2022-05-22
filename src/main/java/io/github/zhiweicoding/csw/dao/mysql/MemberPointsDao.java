package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhiwei
 */
@Component
public interface MemberPointsDao extends BaseMapper<MemberPointsBean> {
}




