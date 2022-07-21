package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.IpadConfigBean;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author zhiwei
 */
@Component
public interface IpadConfigDao extends BaseMapper<IpadConfigBean> {

}




