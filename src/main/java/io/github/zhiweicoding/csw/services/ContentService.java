package io.github.zhiweicoding.csw.services;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhiweicoding.csw.models.ContentBean;

import java.util.List;
import java.util.Map;

/**
 * @author zhiwei
 * @description 针对表【t_content(内容表)】的数据库操作Service
 * @createDate 2022-03-20 15:41:26
 */
public interface ContentService extends IService<ContentBean> {

    /**
     * 小程序 通过mId 获取小程序内容
     *
     * @param mId
     * @return
     */
    Map<String, Object> byId(String mId, int bId, int fId);

    /**
     * 小程序内容简单查询
     * @param msg
     * @return
     */
    List<Map<String, Object>> searchSimple(int fId,String msg);

    /**
     * 小程序内容复杂查询
     * @param title
     * @param msg
     * @param author
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> searchComplex(int fId,String title, String msg, String author, int start, int end);

}
