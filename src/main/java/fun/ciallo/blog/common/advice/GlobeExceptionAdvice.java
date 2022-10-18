package fun.ciallo.blog.common.advice;

import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobeExceptionAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public R<Object> methodArgsNotValidExceptionHandler() {
        return R.state(Status.VALIDATE_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Object> methodNotSupportedExceptionHandler() {
        return R.state(Status.PATH_ERROR);
    }

    @ExceptionHandler(BlogException.class)
    public R<String> recruitExceptionHandler(BlogException e) {
        R<String> result = R.state(e);
        log.error("timestamp------>[{}]------>发生业务异常------>[{}]", result.getTimestamp(), e.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e) {
        R<String> result = R.state(Status.FAILED);
        result.setData(e.getMessage());
        log.error("timestamp------>[{}]------>发生程序异常------>[{}]", result.getTimestamp(), e.getMessage());
        return result;
    }
}
