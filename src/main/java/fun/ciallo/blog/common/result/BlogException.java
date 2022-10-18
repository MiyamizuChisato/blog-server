package fun.ciallo.blog.common.result;

import fun.ciallo.blog.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogException extends RuntimeException {
    private int code;
    private String message;

    public BlogException(Status status) {
        super();
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BlogException message(String message) {
        this.message = message;
        return this;
    }
}
