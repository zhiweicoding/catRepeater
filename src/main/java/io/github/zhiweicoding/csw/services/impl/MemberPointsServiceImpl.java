package io.github.zhiweicoding.csw.services.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhiweicoding.csw.dao.mysql.MemberPointsDao;
import io.github.zhiweicoding.csw.dao.mysql.UserDao;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.MemberPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhiwei
 */
@Service
public class MemberPointsServiceImpl
        extends ServiceImpl<MemberPointsDao, MemberPointsBean>
        implements MemberPointsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MemberPointsDao memberPointsDao;

    @Override
    @Transactional
    public BaseMsg insertBlank(String phone, double leave, double have, int type) {

        List<UserBean> users = userDao.selectList(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone).eq(UserBean::getUserType, "weapp")
                .eq(UserBean::getIsDelete, 0).orderByDesc(UserBean::getCreateTime));
        if (users.size() <= 0) {
            return BaseMsg.get().setCode(-1).setMsg("没有找到用户信息，可以先让用户注册，再来");
        }

        UserBean userBean = users.get(0);
        String userId = userBean.getUserId();

        memberPointsDao.insertBlank(userId, leave, have, type);
        if (users.size() > 1) {
            return BaseMsg.get().setCode(0)
                    .setMsg("虽然成功了，但是用户数据量大于1条，请确认是否有重复的现象，也可以和用户确认一下；或者先使用，清除手机号能找到的所有用户信息，让用户注册后，再试。");
        } else {
            return BaseMsg.get().setCode(0).setMsg("成功");
        }

    }

    @Override
    @Transactional
    public BaseMsg perMonthSendMoney(int type) {

        List<MemberPointsBean> array = memberPointsDao.selectList(Wrappers.<MemberPointsBean>lambdaQuery()
                .eq(MemberPointsBean::getIsDelete, 0)
                .eq(MemberPointsBean::getWhichRadio, 5)
                .gt(MemberPointsBean::getBankMoney, 0)
                .lt(MemberPointsBean::getCreateTime, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0))
                .eq(MemberPointsBean::getPayType, "PCREDIT")
        );

        BigDecimal defaultBig = new BigDecimal("208.33");
        if (type == 10) {
            List<MemberPointsBean> allArray = memberPointsDao.selectList(Wrappers.<MemberPointsBean>lambdaQuery()
                    .eq(MemberPointsBean::getIsDelete, 0)
                    .gt(MemberPointsBean::getBankMoney, 0)
                    .lt(MemberPointsBean::getCreateTime, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0))
                    .eq(MemberPointsBean::getPayType, "ALL")
            );

            array.addAll(allArray);
        } else {
            defaultBig = new BigDecimal("416.66");
        }

        for (MemberPointsBean item : array) {
            Double bankMoney = item.getBankMoney();
            Double leaveMoney = item.getLeaveMoney();
            try {
                if (bankMoney <= defaultBig.doubleValue()) {
                    memberPointsDao.update(null, Wrappers.<MemberPointsBean>lambdaUpdate()
                            .set(MemberPointsBean::getBankMoney, 0)
                            .set(MemberPointsBean::getLeaveMoney, bankMoney + leaveMoney)
                            .eq(MemberPointsBean::getPointsId, item.getPointsId()));
                } else {
                    memberPointsDao.update(null, Wrappers.<MemberPointsBean>lambdaUpdate()
                            .set(MemberPointsBean::getBankMoney, new BigDecimal(bankMoney).subtract(defaultBig).setScale(2, RoundingMode.HALF_UP).doubleValue())
                            .set(MemberPointsBean::getLeaveMoney, bankMoney + leaveMoney)
                            .eq(MemberPointsBean::getPointsId, item.getPointsId()));
                }

            } catch (Exception ignored) {
            }
        }
        return BaseMsg.get().setCode(0).setMsg("成功");
    }
}




