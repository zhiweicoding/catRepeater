package io.github.zhiweicoding.csw.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.GoodBean;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import io.github.zhiweicoding.csw.models.OrderBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.GoodService;
import io.github.zhiweicoding.csw.services.MemberPointsService;
import io.github.zhiweicoding.csw.services.OrderService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhiwei
 */
@RestController
@RequestMapping(value = "/v1/api/user")
@Slf4j
@Tag(name = "用户相关问题")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberPointsService memberPointsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodService goodService;

    @PostMapping("/empty")
    @Operation(summary = "通过手机号删除用户", description = "通过手机号删除对应的用户信息，删除所有垃圾信息")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码"),
            @Parameter(name = "type", required = true, description = "请输入 小程序：weapp;公众号：publicHome；支付宝：alipay")
    })
    public
    @ResponseBody
    BaseResponse<String> empty(HttpServletRequest request,
                               @RequestParam("phone") String phone,
                               @RequestParam("type") String type) {
        boolean remove = userService.remove(Wrappers.<UserBean>lambdaQuery()
                .eq(UserBean::getUserMobile, phone)
                .eq(UserBean::getIsDelete, 0)
                .eq(UserBean::getUserType, type));
        if (remove) {
            return new ResponseFactory<String>().success("删除生成");
        } else {
            return new ResponseFactory<String>().success("删除失败");
        }

    }

    @PostMapping("/query")
    @Operation(summary = "通过手机号查询", description = "通过手机号查询")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码")
    })
    public
    @ResponseBody
    BaseResponse<BaseMsg> query(HttpServletRequest request,
                                @RequestParam("phone") String phone) {
        try {

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
                    Double leaveMoney = memberPointsBean.getLeaveMoney();
                    Double bankMoney = memberPointsBean.getBankMoney();
                    BaseMsg msg = BaseMsg.get().setMsg("操作成功").setCode(0);
                    msg.put("leave", leaveMoney);
                    msg.put("bank", bankMoney);

                    LambdaQueryWrapper<OrderBean> wrapper = Wrappers.<OrderBean>lambdaQuery()
                            .select(OrderBean::getOrderRealPrice)
                            .eq(OrderBean::getUserId, userId)
                            .eq(OrderBean::getIsDelete, 0)
                            .eq(OrderBean::getGoodType, "product")
                            .notIn(OrderBean::getOrderStatus, 0, -1)
                            .orderByDesc(OrderBean::getCreateTime);
                    List<Map<String, Object>> weixinArray = orderService.listMaps(wrapper.isNotNull(OrderBean::getModifyTimeRecord));

                    List<Map<String, Object>> otherArray = orderService.listMaps(wrapper.isNull(OrderBean::getModifyTimeRecord));

                    double weixinSum = weixinArray.stream().mapToDouble(bean -> Double.parseDouble(String.valueOf(bean.get("orderRealPrice")))).sum();
                    double otherSum = otherArray.stream().mapToDouble(bean -> Double.parseDouble(String.valueOf(bean.get("orderRealPrice")))).sum();
                    msg.put("weixinSum", weixinSum);
                    msg.put("otherSum", otherSum);

                    return new ResponseFactory<BaseMsg>().success(msg);
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
