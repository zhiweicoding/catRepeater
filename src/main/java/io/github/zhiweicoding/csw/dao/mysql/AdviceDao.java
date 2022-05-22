package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.AdviceBean;
import org.springframework.stereotype.Component;

/**
 * @author zhiwei
 * @description 针对表【t_advice(意见表)】的数据库操作Mapper
 * @createDate 2022-03-20 15:41:26
 */
@Component
public interface AdviceDao extends BaseMapper<AdviceBean> {
}




