package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.IpadConfigDao;
import io.github.zhiweicoding.csw.dao.mysql.OrderDao;
import io.github.zhiweicoding.csw.models.IpadConfigBean;
import io.github.zhiweicoding.csw.models.OrderBean;
import io.github.zhiweicoding.csw.services.IpadConfigService;
import io.github.zhiweicoding.csw.services.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei
 */
@Service
public class OrderServiceImpl
        extends ServiceImpl<OrderDao, OrderBean>
        implements OrderService {

}




