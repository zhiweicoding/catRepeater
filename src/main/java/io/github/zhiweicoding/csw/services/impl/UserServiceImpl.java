package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.UserDao;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserBean> implements UserService {

}




