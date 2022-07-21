package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.OrderBean;
import io.github.zhiweicoding.csw.models.UserBean;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhiwei
 */
@RestController
@RequestMapping(value = "/v1/api/order")
@Slf4j
@Tag(name = "订单相关问题")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/query")
    @Operation(summary = "通过手机号查询订单", description = "通过手机号查询订单")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码"),
    })
    public
    @ResponseBody
    BaseResponse<List<OrderBean>> empty(HttpServletRequest request,
                                        @RequestParam("phone") String phone) {
        List<OrderBean> resultArray = new ArrayList<>();
        try {
            List<UserBean> list = userService.list(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone));
            if (list.size() > 0) {
                for (UserBean userBean : list) {
                    String userId = userBean.getUserId();
                    if (userId != null) {
                        List<OrderBean> tempList = orderService.list(Wrappers.<OrderBean>lambdaQuery()
                                .select(OrderBean::getAddressUserName, OrderBean::getAddressMobile, OrderBean::getAddressName, OrderBean::getAddressFullRegion, OrderBean::getProductListId, OrderBean::getOrderRealPrice, OrderBean::getOrderActualPrice, OrderBean::getOrderStatus, OrderBean::getCreateTime, OrderBean::getExtParams)
                                .eq(OrderBean::getUserId, userId)
                                .eq(OrderBean::getIsDelete, 0)
                                .notIn(OrderBean::getOrderStatus, 0, -1)
                                .orderByDesc(OrderBean::getCreateTime)
                        );
                        resultArray.addAll(tempList);
                    }
                }
            }

            return new ResponseFactory<List<OrderBean>>().success(resultArray);
        } catch (Exception e) {
            BaseResponse<List<OrderBean>> fail = new ResponseFactory<List<OrderBean>>().fail(resultArray);
            fail.setMsg(e.getMessage());
            return fail;
        }


    }


}
