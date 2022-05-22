package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.ConfigBean;
import org.springframework.stereotype.Component;

/**
* @author zhiwei
* @description 针对表【t_config(配置表)】的数据库操作Mapper
* @createDate 2022-03-20 15:41:26
*/
@Component
public interface ConfigDao extends BaseMapper<ConfigBean> {

}




