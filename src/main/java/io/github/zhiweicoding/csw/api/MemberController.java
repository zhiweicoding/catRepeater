package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.MemberPointsService;
import io.github.zhiweicoding.csw.services.UserService;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhiwei
 */
@RestController
@RequestMapping(value = "/v1/api/member")
@Slf4j
@Tag(name = "充值及相关问题")
public class MemberController {

    @Autowired
    private MemberPointsService memberPointsService;

    @Autowired
    private UserService userService;

    @PostMapping("/saveBlank")
    @Operation(summary = "通过手机号码新建用户信息", description = "通过手机号码，查询用户信息，如果没有找到用户信息，新建用户，解决问题")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码"),
            @Parameter(name = "leave", required = true, description = "APP余额"),
            @Parameter(name = "have", required = true, description = "银行余额"),
            @Parameter(name = "type", required = true, description = "5：每月转账416.66; 10：每月转账208.33")
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> saveBlank(HttpServletRequest request,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("leave") double leave,
                                    @RequestParam("have") double have,
                                    @RequestParam("type") int type) {
        BaseMsg baseMsg = memberPointsService.insertBlank(phone, leave, have, type);
        return new ResponseFactory<BaseMsg>().success(baseMsg);
    }


    @PostMapping("/perMonthSendMoney")
    @Operation(summary = "每月转账", description = "每月转账")
    @Parameters({
            @Parameter(name = "type", required = true, description = "5：每月转账416.66; 10：每月转账208.33")
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> perMonthSendMoney(HttpServletRequest request,
                                            @RequestParam("type") int type) {
        BaseMsg baseMsg = memberPointsService.perMonthSendMoney(type);
        return new ResponseFactory<BaseMsg>().success(baseMsg);
    }

    @PostMapping("/setLeave")
    @Operation(summary = "通过手机号设置余额", description = "通过手机号设置余额")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号"),
            @Parameter(name = "yue", required = true, description = "APP余额"),
            @Parameter(name = "shengyu", required = true, description = "银行余额")
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> setLeave(HttpServletRequest request,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("yue") String yue,
                                   @RequestParam("shengyu") String shengyu) {
        double yueD;
        double shengyuD;
        try {
            yueD = Double.parseDouble(yue);
            shengyuD = Double.parseDouble(shengyu);

            List<UserBean> users = userService.list(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone)
                    .eq(UserBean::getIsDelete, 0)
                    .eq(UserBean::getUserType, "weapp"));

            if (users.size() == 1) {
                UserBean userBean = users.get(0);
                String userId = userBean.getUserId();

                List<MemberPointsBean> ms = memberPointsService.list(Wrappers.<MemberPointsBean>lambdaQuery()
                        .eq(MemberPointsBean::getUserId, userId)
                        .gt(MemberPointsBean::getBankMoney, 0)
                        .eq(MemberPointsBean::getIsDelete, 0));
                if (ms.size() > 1) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("充值单大于1，有问题，什么都没操作").setCode(-1));
                } else if (ms.size() <= 0) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("无充值单或者银行账户为0,什么都没操作").setCode(-1));
                } else {
                    MemberPointsBean memberPointsBean = ms.get(0);
                    memberPointsService.update(Wrappers.<MemberPointsBean>lambdaUpdate()
                            .set(MemberPointsBean::getBankMoney, shengyuD)
                            .set(MemberPointsBean::getLeaveMoney, yueD)
                            .eq(MemberPointsBean::getPointsId, memberPointsBean.getPointsId()));
                    return new ResponseFactory<BaseMsg>().success(BaseMsg.get().setMsg("操作成功").setCode(0));
                }
            } else if (users.size() > 1) {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("用户数量大于1，什么都没操作").setCode(-1));
            } else {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("找不到用户").setCode(-1));
            }
        } catch (NumberFormatException e) {
            return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("金额有误").setCode(-1));
        }

    }

    @PostMapping("/preStore")
    @Operation(summary = "预存", description = "预存")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号"),
            @Parameter(name = "money", required = true, description = "金额"),
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> preStore(HttpServletRequest request,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("money") String money) {
        double moneyD;
        try {
            moneyD = Double.parseDouble(money);

            List<UserBean> users = userService.list(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone)
                    .eq(UserBean::getIsDelete, 0)
                    .eq(UserBean::getUserType, "weapp"));

            if (users.size() == 1) {
                UserBean userBean = users.get(0);
                String userId = userBean.getUserId();

                List<MemberPointsBean> ms = memberPointsService.list(Wrappers.<MemberPointsBean>lambdaQuery()
                        .eq(MemberPointsBean::getUserId, userId)
                        .gt(MemberPointsBean::getBankMoney, 0)
                        .eq(MemberPointsBean::getIsDelete, 0));
                if (ms.size() > 1) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("充值单大于1，有问题，什么都没操作").setCode(-1));
                } else if (ms.size() <= 0) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("无充值单或者银行账户为0,什么都没操作").setCode(-1));
                } else {
                    MemberPointsBean memberPointsBean = ms.get(0);
                    Double bankMoney = memberPointsBean.getBankMoney();
                    Double leaveMoney = memberPointsBean.getLeaveMoney();

                    double gapMoney = moneyD;
                    if (bankMoney > moneyD) {
                        memberPointsService.update(Wrappers.<MemberPointsBean>lambdaUpdate()
                                .set(MemberPointsBean::getBankMoney, bankMoney - moneyD)
                                .set(MemberPointsBean::getLeaveMoney, leaveMoney + moneyD)
                                .eq(MemberPointsBean::getPointsId, memberPointsBean.getPointsId()));
                    } else {
                        memberPointsService.update(Wrappers.<MemberPointsBean>lambdaUpdate()
                                .set(MemberPointsBean::getBankMoney, 0)
                                .set(MemberPointsBean::getLeaveMoney, leaveMoney + bankMoney)
                                .eq(MemberPointsBean::getPointsId, memberPointsBean.getPointsId()));
                        gapMoney = bankMoney;
                    }
                    return new ResponseFactory<BaseMsg>().success(BaseMsg.get().setMsg("操作成功,充值金额：" + gapMoney).setCode(0));
                }
            } else if (users.size() > 1) {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("用户数量大于1，什么都没操作").setCode(-1));
            } else {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("找不到用户").setCode(-1));
            }
        } catch (NumberFormatException e) {
            return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("金额有误").setCode(-1));
        }

    }

    @PostMapping("/recharge")
    @Operation(summary = "充值", description = "充值")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号"),
            @Parameter(name = "money", required = true, description = "金额"),
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> recharge(HttpServletRequest request,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("money") String money) {
        double moneyD;
        try {
            moneyD = Double.parseDouble(money);

            List<UserBean> users = userService.list(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone)
                    .eq(UserBean::getIsDelete, 0)
                    .eq(UserBean::getUserType, "weapp"));

            if (users.size() == 1) {
                UserBean userBean = users.get(0);
                String userId = userBean.getUserId();

                List<MemberPointsBean> ms = memberPointsService.list(Wrappers.<MemberPointsBean>lambdaQuery()
                        .eq(MemberPointsBean::getUserId, userId)
                        .eq(MemberPointsBean::getIsDelete, 0));
                if (ms.size() > 1) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("充值单大于1，有问题，什么都没操作").setCode(-1));
                } else if (ms.size() <= 0) {
                    return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("无充值单,什么都没操作").setCode(-1));
                } else {
                    MemberPointsBean memberPointsBean = ms.get(0);
                    Double leaveMoney = memberPointsBean.getLeaveMoney();

                    memberPointsService.update(Wrappers.<MemberPointsBean>lambdaUpdate()
                            .set(MemberPointsBean::getLeaveMoney, leaveMoney + moneyD)
                            .eq(MemberPointsBean::getPointsId, memberPointsBean.getPointsId()));
                    return new ResponseFactory<BaseMsg>().success(BaseMsg.get().setMsg("操作成功,充值金额：" + money).setCode(0));
                }
            } else if (users.size() > 1) {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("用户数量大于1，什么都没操作").setCode(-1));
            } else {
                return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("找不到用户").setCode(-1));
            }
        } catch (NumberFormatException e) {
            return new ResponseFactory<BaseMsg>().fail(BaseMsg.get().setMsg("金额有误").setCode(-1));
        }

    }
}
