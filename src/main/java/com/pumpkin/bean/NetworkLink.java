package com.pumpkin.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @ClassName NetworkLink
 * @Author 孙博
 * @Date 2019/1/18 下午8:30
 * @Version 1.0
 */
@Data
public class NetworkLink {

    @Id
    private String id;

    // Logo
    private String logo;

    // 网站名称
    private String name;

    // 描述
    private String desc;

    // 地址
    private String url;

    // 排序
    private Integer order;

}
