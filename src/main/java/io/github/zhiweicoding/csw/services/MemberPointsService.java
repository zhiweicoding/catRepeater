package io.github.zhiweicoding.csw.services;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.MemberPointsBean;

/**
 * @author zhiwei
 */
public interface MemberPointsService extends IService<MemberPointsBean> {

    BaseMsg insertBlank(String phone, double leave, double have, int type);

    BaseMsg perMonthSendMoney(int type);
}
