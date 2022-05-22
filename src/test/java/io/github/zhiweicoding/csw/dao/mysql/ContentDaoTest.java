package io.github.zhiweicoding.csw.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhiweicoding.csw.models.ContentBean;
import io.github.zhiweicoding.csw.models.ImgBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @Created by zhiwei on 2022/3/20.
 */
@SpringBootTest
@Slf4j
public class ContentDaoTest {

    @Autowired
    private ContentDao contentDao;
    @Autowired
    private ImgDao imgDao;

    @Test
    public void pageTest() {
        Page<ContentBean> page = new Page<>(0, 3);
        LambdaQueryWrapper<ContentBean> wrapper = Wrappers.<ContentBean>lambdaQuery().ge(ContentBean::getBookId, 1).orderByAsc(ContentBean::getCreateTime);
        Page<ContentBean> result = contentDao.selectPage(page, wrapper);
        log.error(String.valueOf(result));
        System.out.println(result);
    }

    /**
     * 2020年，双桥河镇实现工业税收4560万元，同比下降14.67%。盘活低效载体7444.19平方米，完成新一轮中小企业创新转型8家，其中新增6家。通过国家科技型中小企业认定44家，完成全年任务的100%。通过“瞪羚”企业认定1家，通过“雏鹰”企业认定9家。国家级高新技术企业有效数20家，2家已完成重新认定。申报科技计划项目3家。鼓励企业家参与社会公益慈善活动，4家企业累计投资100万元参与西部贫困县帮扶投资项目。“双万双服”工作组帮助45家企业解决问题130个，实现问题收集率90.28%，解决率100%。
     * （王力彬）
     */
    @Test
    public void pageTest2() {
        List<ContentBean> contentBeans = contentDao.selectList(Wrappers.<ContentBean>lambdaQuery()
                .isNotNull(ContentBean::getOrgMsg)
                .isNull(ContentBean::getContentAuthor));

        for (ContentBean contentBean : contentBeans) {
            String orgMsg = contentBean.getOrgMsg();
            String trim = orgMsg.trim();
            String[] split = trim.split(System.lineSeparator());
            if (split.length > 1) {
                String temp = split[split.length - 1];
                if ((temp.startsWith("（") && temp.endsWith("）"))
                        || temp.startsWith("(") && temp.endsWith(")")
                        || temp.startsWith("（") && temp.endsWith(")")
                        || temp.startsWith("(") && temp.endsWith("）")) {
                    LambdaUpdateWrapper<ContentBean> eq = Wrappers.<ContentBean>lambdaUpdate()
                            .set(ContentBean::getContentAuthor, temp.replace("（", "").replace(")", "").replace("(", "").replace("）", ""))
                            .eq(ContentBean::getContentId, contentBean.getContentId());
                    contentDao.update(null, eq);
                }
            }
        }
    }

    @Test
    public void test1() {
        List<ImgBean> imgBeans = imgDao.selectList(Wrappers.<ImgBean>lambdaQuery().eq(ImgBean::getIsDelete, 0));
        for (ImgBean imgBean : imgBeans) {
            String imgMsg = imgBean.getImgMsg();
            String[] split = imgMsg.split("src=");
            String result = split[0] + " mode=\"widthFix\" " + split[1];
            imgDao.update(null, Wrappers.<ImgBean>lambdaUpdate().set(ImgBean::getImgMsg, result));
        }
    }

    @Test
    public void test2() {
        List<ImgBean> imgBeans = imgDao.selectList(Wrappers.<ImgBean>lambdaQuery().eq(ImgBean::getIsDelete, 0));
        for (ImgBean imgBean : imgBeans) {
            String imgMsg = imgBean.getImgMsg();
            String[] split = imgMsg.split("mode=\"widthFix\" ");
            String result = split[0] + "mode=\"widthFix\" src=" + split[1];
            imgDao.update(null, Wrappers.<ImgBean>lambdaUpdate().set(ImgBean::getImgMsg, result));
        }
    }

    //<img class="imgCls"  mode="widthFix" src="https://booklib-1309974988.cos.ap-shanghai.myqcloud.com/%E6%B4%A5%E5%8D%97-2.jpg"/>
    @Test
    public void test3() {
        List<ImgBean> imgBeans = imgDao.selectList(Wrappers.<ImgBean>lambdaQuery().eq(ImgBean::getIsDelete, 0));
        for (ImgBean imgBean : imgBeans) {
            String orgUrl = imgBean.getOrgUrl();
            String result = "<img class=\"imgCls\"  mode=\"widthFix\" src=\"" + orgUrl + "\"/>";
            imgDao.update(null, Wrappers.<ImgBean>lambdaUpdate().set(ImgBean::getImgMsg, result).eq(ImgBean::getImgId, imgBean.getImgId()));
        }
    }

    @Test
    public void test4() {
        List<Map<String, Object>> yqs = contentDao.qLike(3, "疫情");
        System.out.println(yqs.size());
    }

    @Test
    public void test5() {
        List<Map<String, Object>> h = contentDao.qLike(3, "红十字会");
        System.out.println(h);
    }

    @Test
    public void test6() {
        List<Map<String, Object>> h = contentDao.qSearch(3,
                "红十字会",
                null,null,2020,2022);
        System.out.println(h);
    }

}