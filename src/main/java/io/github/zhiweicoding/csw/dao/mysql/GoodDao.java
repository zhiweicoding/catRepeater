package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhiweicoding.csw.models.GoodBean;
import org.springframework.stereotype.Component;

/**
 * @Created by zhiwei on 2022/3/25.
 */
@Component
public interface GoodDao extends BaseMapper<GoodBean> {
}
