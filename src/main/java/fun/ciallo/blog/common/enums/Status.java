package fun.ciallo.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    SUCCESS(1, "操作成功"),
    FAILED(-1, "内部错误"),
    USER_ERROR(-200, "用户相关信息错误"),
    VALIDATE_ERROR(-400, "错误的参数"),
    PATH_ERROR(-404, "未找到请求的资源");

    private final int code;
    private final String message;
}
