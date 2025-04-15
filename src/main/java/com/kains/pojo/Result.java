package com.kains.pojo;

/**
 * 自定义返回结果类
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> error(T msg) {
        return new Result<>(0, "error", msg);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(1, "success", data);
    }
    public static <T> Result<T> success() {
        return new Result<>(1, "success", null);
    }
    public static <T> Result<T> error() {
        return new Result<>(0, "error", null);
    }
}