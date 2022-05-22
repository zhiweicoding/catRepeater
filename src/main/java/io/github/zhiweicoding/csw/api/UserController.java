package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
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


}
