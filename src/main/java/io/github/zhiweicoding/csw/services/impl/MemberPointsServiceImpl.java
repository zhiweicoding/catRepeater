package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.MemberPointsDao;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import io.github.zhiweicoding.csw.services.MemberPointsService;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei
 */
@Service
public class MemberPointsServiceImpl
        extends ServiceImpl<MemberPointsDao, MemberPointsBean>
        implements MemberPointsService {
}




