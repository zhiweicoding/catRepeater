package io.github.zhiweicoding.csw.api;

import io.github.zhiweicoding.csw.constants.RedisConstants;
import io.github.zhiweicoding.csw.models.BaseResponse;
import io.github.zhiweicoding.csw.services.ContentService;
import io.github.zhiweicoding.csw.services.SearchService;
import io.github.zhiweicoding.csw.support.RedisSupport;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.github.zhiweicoding.csw.tool.PageNumNextTool;
import io.github.zhiweicoding.csw.tool.bean.PageHelperBean;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * TODO 红色的变色
 * TODO checkValue 没有值 undefined
 *
 * @Created by zhiwei on 2022/3/11.
 */
@RestController
@RequestMapping(value = "/v1/api/search")
@Slf4j
@Tag(name = "查询接口")
public class SearchController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private RedisSupport redisSupport;

    /**
     * <div>【北辰区概况】</div>
     * '<div>北辰区位于天津市中心城区北部，处于京滨综合发展轴中心的位置。</div>'
     *
     * @param request
     * @param checkValue
     * @param switchType
     * @param title
     * @param msg
     * @param author
     * @param start
     * @param end
     * @return
     */
    @PostMapping("/base")
    public
    @ResponseBody
    BaseResponse<PageHelperBean> base(HttpServletRequest request,
                                      @RequestParam("pageNum") int pageNum,
                                      @RequestParam("fId") int fId,
                                      @RequestParam("checkValue") String checkValue,
                                      @RequestParam("switchType") int switchType,
                                      @RequestParam("title") String title,
                                      @RequestParam("msg") String msg,
                                      @RequestParam("author") String author,
                                      @RequestParam("start") int start,
                                      @RequestParam("end") int end) {
        List<?> array;
        if (switchType == 1) {
            if (Boolean.parseBoolean(checkValue)) {
                String keyPart = "multi-search" + fId + title + msg + author + start + end;
                String encode = URLEncoder.encode(keyPart, StandardCharsets.UTF_8);
                String format = String.format(RedisConstants.search_key, encode);
                boolean exists = redisSupport.exists(format);
                if (exists) {
                    array = (List<?>) redisSupport.get(format);
                } else {
                    array = searchService.searchComplex(fId, title, msg, author, start, end);
                }
            } else {
                String keyPart = "simple-search" + fId + msg;
                String encode = URLEncoder.encode(keyPart, StandardCharsets.UTF_8);
                String format = String.format(RedisConstants.search_key, encode);
                boolean exists = redisSupport.exists(format);
                if (exists) {
                    array = (List<?>) redisSupport.get(format);
                } else {
                    array = searchService.searchSimple(fId, msg);
                }

            }
        } else {
            if (Boolean.parseBoolean(checkValue)) {
                String keyPart = "multi-img" + fId + title + msg + author + start + end;
                String format = String.format(RedisConstants.search_key, URLEncoder.encode(keyPart, StandardCharsets.UTF_8));
                boolean exists = redisSupport.exists(format);
                if (exists) {
                    array = (List<?>) redisSupport.get(format);
                }
            } else {
                String keyPart = "simple-img" + fId + msg;
                String format = String.format(RedisConstants.search_key, URLEncoder.encode(keyPart, StandardCharsets.UTF_8));
                boolean exists = redisSupport.exists(format);
                if (exists) {
                    array = (List<?>) redisSupport.get(format);
                }
            }
        }
        PageNumNextTool<PageHelperBean> tool = new PageNumNextTool<>();
        PageHelperBean bean = new PageHelperBean<>();
        bean.setTAllList(new ArrayList());
        bean.setEveryPageCount(15);
        bean.setWhichPageNum(pageNum);
        PageHelperBean work = tool.work(bean);
        return new ResponseFactory<PageHelperBean>().success(work);
    }
}
