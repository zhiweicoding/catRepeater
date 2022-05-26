package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.entity.api.HomeBase;
import io.github.zhiweicoding.csw.models.UserBean;
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
import java.util.Random;

/**
 * @author zhiwei
 */
@RestController
@RequestMapping(value = "/v1/api/fake")
@Slf4j
@Tag(name = "模拟调用")
public class FakeController {

    @Autowired
    private UserService userService;

    @PostMapping("/one")
    @Operation(summary = "获取首页数据", description = "获取首页数据")
    public
    @ResponseBody
    BaseResponse<HomeBase> one() {
        ResponseFactory<HomeBase> factory = new ResponseFactory<>();

        HomeBase homeBase = new HomeBase();
        List<HomeBase.OrderBase> os = new ArrayList<>();
        HomeBase.OrderBase one = new HomeBase.OrderBase();
        one.setName("所有订单");
        one.setOrder(1);
        one.setUrl("1");
        one.setNumber(102);
        os.add(one);

        HomeBase.OrderBase two = new HomeBase.OrderBase();
        two.setName("待付款");
        two.setOrder(2);
        two.setUrl("2");
        two.setNumber(9);
        os.add(two);
        homeBase.setOrders(os);

        HomeBase.OrderBase three = new HomeBase.OrderBase();
        three.setName("待发货");
        three.setOrder(3);
        three.setUrl("3");
        three.setNumber(13);
        os.add(three);
        homeBase.setOrders(os);

        HomeBase.OrderBase four = new HomeBase.OrderBase();
        four.setName("待收货");
        four.setOrder(4);
        four.setUrl("4");
        four.setNumber(23);
        os.add(four);
        homeBase.setOrders(os);

        HomeBase.OrderBase five = new HomeBase.OrderBase();
        five.setName("待评价");
        five.setOrder(5);
        five.setUrl("5");
        five.setNumber(41);
        os.add(five);
        homeBase.setOrders(os);

        List<HomeBase.SellBase> cats = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            HomeBase.SellBase s = new HomeBase.SellBase();
            Random random = new Random();
            int randomInt = random.nextInt(9999999);
            s.setMoney(randomInt);
            s.setMonthStr("2022年" + (i < 10 ? ("0" + i) : String.valueOf(i)) + "月");
            s.setOrder(i - 1);
            s.setUrl(String.valueOf(i - 1));
            cats.add(s);
        }

        homeBase.setCatSells(cats);

        List<HomeBase.SellBase> pros = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            HomeBase.SellBase s = new HomeBase.SellBase();
            Random random = new Random();
            int randomInt = random.nextInt(9999999);
            s.setMoney(randomInt);
            s.setMonthStr("2022年" + (i < 10 ? ("0" + i) : String.valueOf(i)) + "月");
            s.setOrder(i - 1);
            s.setUrl(String.valueOf(i - 1));
            pros.add(s);
        }

        homeBase.setProductSells(pros);

        List<HomeBase.SellBase> adopts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            HomeBase.SellBase s = new HomeBase.SellBase();
            Random random = new Random();
            int randomInt = random.nextInt(9999999);
            s.setMoney(randomInt);
            s.setMonthStr("2022年" + (i < 10 ? ("0" + i) : String.valueOf(i)) + "月");
            s.setOrder(i - 1);
            s.setUrl(String.valueOf(i - 1));
            adopts.add(s);
        }

        homeBase.setAdoptSell(adopts);

        Random random = new Random();
        homeBase.setProtectNum(random.nextInt(9999));
        homeBase.setOpinionNum(random.nextInt(9999));
        homeBase.setMonthUserNum(random.nextInt(9999));
        homeBase.setAdoptUserNum(random.nextInt(9999));

        return factory.success(homeBase);
    }


}
