package io.github.zhiweicoding.csw.api;

import io.github.zhiweicoding.csw.models.BaseResponse;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆
 *
 * @Created by zhiwei on 2022/3/11.
 */
@RestController
@RequestMapping(value = "/v1/api/login")
@Slf4j
public class LoginController {


    /**
     * 15 min cache
     *
     * @param request
     * @param fId
     * @return
     */
    @PostMapping("/check")
    public
    @ResponseBody
    BaseResponse<Map<String, String>> check(HttpServletRequest request, @RequestParam("user") String user,
                                            @RequestParam("pwd") String pwd) {
        System.out.println(user);
        System.out.println(pwd);
        return new ResponseFactory<Map<String, String>>().success(new HashMap<>());
    }


}
