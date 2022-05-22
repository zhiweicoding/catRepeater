package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.ConfigDao;
import io.github.zhiweicoding.csw.models.ConfigBean;
import io.github.zhiweicoding.csw.services.ConfigService;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei
 * @description 针对表【t_config(配置表)】的数据库操作Service实现
 * @createDate 2022-03-20 15:41:26
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigBean> implements ConfigService {

}




