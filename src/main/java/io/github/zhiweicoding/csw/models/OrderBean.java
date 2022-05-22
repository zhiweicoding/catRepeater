package io.github.zhiweicoding.csw.models;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Created by zhiwei on 2022/5/22.
 */
@TableName(value = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "用户信息表", description = "用户信息表")
public class OrderBean {
}
