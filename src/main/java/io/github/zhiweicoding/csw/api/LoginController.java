package io.github.zhiweicoding.csw.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.entity.BaseResponse;
import io.github.zhiweicoding.csw.models.IpadConfigBean;
import io.github.zhiweicoding.csw.models.UserBean;
import io.github.zhiweicoding.csw.services.IpadConfigService;
import io.github.zhiweicoding.csw.services.UserService;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.github.zhiweicoding.csw.utils.HttpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhiwei
 */
@RestController
@RequestMapping(value = "/v1/api/login")
@Slf4j
@Tag(name = "登陆相关问题")
public class LoginController {

    public static final String login_one = "api/v1/wc/login/loginqr";
    public static final String login_two = "api/v1/wc/login/login_info";
    public static final String login_three = "api/v1/wc/login/login_newinit";

    @Autowired
    private IpadConfigService ipadConfigService;

    @PostMapping("/scan")
    @Operation(summary = "第一步获取二维码", description = "输入周杰伦、王一博、易烊千玺")
    @Parameters({
            @Parameter(name = "name", required = true, description = "周杰伦、王一博、易烊千玺选一个"),
            @Parameter(name = "key", required = true, description = "http://localhost:5005/wx/client 获取的最后一个字符串")
    })
    public
    @ResponseBody
    BaseResponse<Map<String, String>> scan(HttpServletRequest request,
                                           @RequestParam("name") String name,
                                           @RequestParam("key") String key) {
        IpadConfigBean bean = ipadConfigService.getOne(Wrappers.<IpadConfigBean>lambdaQuery().eq(IpadConfigBean::getConfigContent, name));
        Map<String, String> params = new HashMap<>();
        params.put("wcid", bean.getConfigUrl());
        params.put("proxy_area_code", "BeiJ");
        params.put("force_proxy_area", "1");
        params.put("clientnet_url", key);
        String responseStr = HttpUtil.postFormUrlEncoded(bean.getOtherMsg() + login_one, bean.getConfigMsg(), JSON.toJSONString(params));
        JSONObject resJson = JSON.parseObject(responseStr);
        int code = resJson.getIntValue("code");
        if (code == 0) {
            JSONObject data = resJson.getJSONObject("data");
            String qr_data = data.getString("qr_data");
            String wcid = data.getString("wcid");

            Map<String, String> resDic = new HashMap<>();
            resDic.put("qrCode", qr_data);
            resDic.put("wcid", wcid);
            return new ResponseFactory<Map<String, String>>().success(resDic);
        } else {
            Map<String, String> resDic = new HashMap<>();
            resDic.put("msg", "失败了，重试");
            return new ResponseFactory<Map<String, String>>().fail(resDic);
        }

    }

    @PostMapping("/login")
    @Operation(summary = "第2步登陆", description = "第2步登陆")
    @Parameters({
            @Parameter(name = "name", required = true, description = "周杰伦、王一博、易烊千玺选一个,和第一步对应"),
            @Parameter(name = "wcid", required = true, description = "wcid 第一步返回的wcid")
    })
    public
    @ResponseBody
    BaseResponse<String> login(HttpServletRequest request,
                               @RequestParam("name") String name,
                               @RequestParam("wcid") String wcid) {
        IpadConfigBean bean = ipadConfigService.getOne(Wrappers.<IpadConfigBean>lambdaQuery().eq(IpadConfigBean::getConfigContent, name));
        Map<String, String> params = new HashMap<>();
        params.put("wcid", wcid);
        String responseStr = HttpUtil.postFormUrlEncoded(bean.getOtherMsg() + login_two, bean.getConfigMsg(), JSON.toJSONString(params));
        JSONObject resJson = JSON.parseObject(responseStr);
        int code = resJson.getIntValue("code");
        if (code == 0) {
            String responseStr2 = HttpUtil.postFormUrlEncoded(bean.getOtherMsg() + login_three, bean.getConfigMsg(), JSON.toJSONString(params));
            JSONObject resJson2 = JSON.parseObject(responseStr2);
            int code2 = resJson2.getIntValue("code");
            if (code2 == 0) {
                ipadConfigService.update(Wrappers.<IpadConfigBean>lambdaUpdate()
                        .set(IpadConfigBean::getConfigUrl, wcid)
                        .eq(IpadConfigBean::getConfigId, bean.getConfigId()));
                return new ResponseFactory<String>().success("成功，请重启控制台");
            } else {
                return new ResponseFactory<String>().fail("第2步失败了");
            }

        } else {
            return new ResponseFactory<String>().fail("第1步失败了");
        }

    }

}
