package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseMsg;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.MemberPointsBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.MemberPointsService;
import io.github.zhiweicoding.csw.services.UserService;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.github.zhiweicoding.csw.utils.GeneratorUtils;
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

    @PostMapping("/saveBlank")
    @Operation(summary = "通过手机号码新建用户信息", description = "通过手机号码，查询用户信息，如果没有找到用户信息，新建用户，解决问题")
    @Parameters({
            @Parameter(name = "phone", required = true, description = "手机号码"),
            @Parameter(name = "leave", required = true, description = "账户剩余钱数"),
            @Parameter(name = "have", required = true, description = "充值到账钱数"),
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


}
