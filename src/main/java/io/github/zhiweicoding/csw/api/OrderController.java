package io.github.zhiweicoding.csw.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.GoodBean;
import io.github.zhiweicoding.csw.models.OrderBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.GoodService;
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
import java.util.*;

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

    @Autowired
    private GoodService goodService;

    @PostMapping("/query")
    @Operation(summary = "通过手机号查询订单", description = "通过手机号查询订单")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码"),
    })
    public
    @ResponseBody
    BaseResponse<List<Map<String, Object>>> empty(HttpServletRequest request,
                                                  @RequestParam("phone") String phone) {
        List<Map<String, Object>> resultArray = new ArrayList<>();
        try {
            List<UserBean> list = userService.list(Wrappers.<UserBean>lambdaQuery().eq(UserBean::getUserMobile, phone));
            if (list.size() > 0) {
                for (UserBean userBean : list) {
                    String userId = userBean.getUserId();
                    if (userId != null) {
                        List<Map<String, Object>> tempList = orderService.listMaps(Wrappers.<OrderBean>lambdaQuery()
                                .select(OrderBean::getAddressUserName, OrderBean::getAddressMobile, OrderBean::getAddressName, OrderBean::getAddressFullRegion, OrderBean::getProductListId, OrderBean::getOrderRealPrice, OrderBean::getOrderActualPrice, OrderBean::getOrderStatus, OrderBean::getCreateTime, OrderBean::getExtParams)
                                .eq(OrderBean::getUserId, userId)
                                .eq(OrderBean::getIsDelete, 0)
                                .eq(OrderBean::getGoodType, "product")
                                .notIn(OrderBean::getOrderStatus, 0, -1)
                                .orderByDesc(OrderBean::getCreateTime)
                        );
                        resultArray.addAll(tempList);
                    }
                }
            }

            if (resultArray.size() > 0) {
                List<GoodBean> productArray = goodService.list(Wrappers.<GoodBean>lambdaQuery()
                        .select(GoodBean::getGoodTitle, GoodBean::getGoodId, GoodBean::getListPicUrl, GoodBean::getGoodBrief, GoodBean::getRetailPrice)
                        .eq(GoodBean::getGoodType, "product")
                        .eq(GoodBean::getIsDelete, 0));
                for (Map<String, Object> orderBean : resultArray) {
                    String productListId = String.valueOf(orderBean.get("product_list_id"));
                    JSONArray productList = JSON.parseArray(productListId);
                    for (int i = 0; i < productList.size(); i++) {
                        JSONObject productItem = productList.getJSONObject(i);
                        Optional<GoodBean> first = productArray.stream()
                                .filter(bean -> bean.getGoodId().trim().equals(productItem.getString("id").trim()))
                                .findFirst();
                        if (first.isPresent()) {
                            GoodBean goodBean = first.get();
                            productItem.put("good_title", goodBean.getGoodTitle());
                            productItem.put("good_brief", goodBean.getGoodBrief());
                            productItem.put("good_retail_price", goodBean.getRetailPrice());
                            productItem.put("list_pic_url", goodBean.getListPicUrl());
                        }
                    }
                    orderBean.put("product_list_id", JSON.toJSONString(productList));
                }
            }

            return new ResponseFactory<List<Map<String, Object>>>().success(resultArray);
        } catch (Exception e) {
            BaseResponse<List<Map<String, Object>>> fail = new ResponseFactory<List<Map<String, Object>>>().fail(resultArray);
            fail.setMsg(e.getMessage());
            return fail;
        }


    }


}
