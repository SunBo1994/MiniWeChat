package com.pumpkin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseBean
 * @Author 孙博
 * @Date 2019/1/18 下午5:43
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {
    /**
     * 状态码
     *  0   成功
     *  -1  失败
     */
    private int code;
    /**
     * 状态信息
     *  success 成功
     *  error 失败
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 正常返回
     */
    public static ResponseBean success(Object object){
        return new ResponseBean(0, "success", object);
    }

    /**
     * 错误返回
     */
    public static ResponseBean error(Exception exception){
        return new ResponseBean(-1, "error", exception.getMessage());
    }
}
