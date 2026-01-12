package com.xzit.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 31507
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    private Integer code;
    private String message;
    private Boolean success;
    private T data;

    private Result() {

    }

    public static <T> Result<T> success() {
        // 创建一个新的Result对象，并设置其成功状态、状态码和消息
// 这里使用了链式调用的方式来初始化对象，提高了代码的可读性和简洁性
        return new Result<T>()
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMessage("操作成功");

    }

    /**
     * 创建一个成功响应的结果对象
     * 该方法用于当操作成功时，封装成功数据和信息
     *
     * @param <T>  泛型参数，表示结果对象中数据的类型
     * @param data 成功操作后返回的数据
     * @return 返回一个成功结果对象，包含成功状态、成功代码、默认的成功消息和操作数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>().setSuccess(true).setCode(ResultCode.SUCCESS).setMessage("操作成功").setData(data);
    }

    /**
     * 创建一个失败响应的结果对象
     * 该方法用于当操作失败时，封装错误信息
     *
     * @param <T> 泛型参数，表示结果对象中数据的类型
     * @return 返回一个失败结果对象，包含失败状态、错误代码和默认的错误消息
     */
    public static <T> Result<T> fail() {
        return new Result<T>().setSuccess(false).setCode(ResultCode.ERROR).setMessage("操作失败");
    }

    public static Result fail(String message) {
        return new Result().setSuccess(false).setCode(ResultCode.ERROR).setMessage(message);
    }
}
