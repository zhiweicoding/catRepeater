package io.github.zhiweicoding.csw.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.zhiweicoding.csw.dao.mysql.MenuDao;
import io.github.zhiweicoding.csw.models.BaseResponse;
import io.github.zhiweicoding.csw.models.BookBean;
import io.github.zhiweicoding.csw.models.MenuBean;
import io.github.zhiweicoding.csw.services.BookService;
import io.github.zhiweicoding.csw.services.MenuService;
import io.github.zhiweicoding.csw.support.ResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * 目录
 *
 * @Created by zhiwei on 2022/3/11.
 */
@RestController
@RequestMapping(value = "/v1/api/menu")
@Slf4j
@Tag(name = "目录接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MenuDao menuDao;

    @Cacheable(value = "15m", key = "#bId", unless = "#result == null")
    @PostMapping("/base")
    public
    @ResponseBody
    BaseResponse<Map<String, Object>> base(HttpServletRequest request, @RequestParam("bId") int bId, @RequestParam("fId") int fId) {
        Map<String, Object> miniDict = menuService.byId(bId, fId);
        return new ResponseFactory<Map<String, Object>>().success(miniDict);
    }

    @Cacheable(value = "15m", key = "#bId+'-'+#mId", unless = "#result == null")
    @PostMapping("/getSon")
    public
    @ResponseBody
    BaseResponse<Map<String, Object>> getSon(HttpServletRequest request, @RequestParam("mId") String mId, @RequestParam("bId") int bId, @RequestParam("fId") int fId) {
        Map<String, Object> miniDict = menuService.getSonByFatherId(mId, bId, fId);
        return new ResponseFactory<Map<String, Object>>().success(miniDict);
    }

    @PostMapping("/click")
    public
    @ResponseBody
    BaseResponse<Boolean> click(HttpServletRequest request, @RequestParam("mId") String mId) {
        MenuBean menuBean = menuDao.selectById(mId);
        BookBean byId = bookService.getById(menuBean.getBookId());
        int i = new Random().nextInt(10);
        if (i < 1) {
            i = 1;
        }
        bookService.update(null, Wrappers.<BookBean>lambdaUpdate().set(BookBean::getReadNum, byId.getReadNum() + i).eq(BookBean::getBookId, menuBean.getBookId()));
        menuService.update(null, Wrappers.<MenuBean>lambdaUpdate().set(MenuBean::getReadNum, menuBean.getReadNum() + i).eq(MenuBean::getMenuId, mId));
//        menuDao.readAddOne(mId);
        return new ResponseFactory<Boolean>().success(true);
    }
}
