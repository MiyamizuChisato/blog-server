package fun.ciallo.blog.common.result;

import fun.ciallo.blog.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private T data;
    private int code;
    private String message;
    private final long timestamp = System.currentTimeMillis();

    public R<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public R<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public R<T> data(T data) {
        this.setData(data);
        return this;
    }

    public static <T> R<T> state(Status status) {
        R<T> result = new R<>();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        return result;
    }

    public static R<String> state(BlogException exception) {
        R<String> result = new R<>();
        result.setCode(exception.getCode());
        result.setMessage(exception.getMessage());
        return result;
    }
}
