package io.github.zhiweicoding.csw.api;

import io.github.zhiweicoding.csw.models.BaseResponse;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @Created by zhiwei on 2022/3/11.
 */
@RestController
@RequestMapping(value = "/v1/api/home")
@Slf4j
@Tag(name = "获取基本订单信息")
public class HomeController {

    /**
     * 15 min cache
     *
     * @param request
     * @return
     */
    @PostMapping("/base")
    @Operation(summary = "获取所有首页数据", description = "获取所有首页数据")
    @Parameters({
            @Parameter(name = "fId", required = true, description = "fId")
    })
    public
    @ResponseBody
    BaseResponse<List<BookBean>> base(HttpServletRequest request) {
        return new ResponseFactory<List<BookBean>>().success(new ArrayList<>());
    }


}
