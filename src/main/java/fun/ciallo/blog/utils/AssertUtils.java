package fun.ciallo.blog.utils;
import fun.ciallo.blog.common.result.BlogException;
import org.springframework.stereotype.Component;

@Component
public class AssertUtils {
    public void isNull(Object o, BlogException e) {
        if (null != o) {
            throw e;
        }
    }

    public void notNull(Object o, BlogException e) {
        if (null == o) {
            throw e;
        }
    }

    public void isTrue(boolean b, BlogException e) {
        if (!b) {
            throw e;
        }
    }

    public void notTrue(boolean b, BlogException e) {
        if (b) {
            throw e;
        }
    }

    public void equals(Object o1, Object o2, BlogException e) {
        if (!o1.equals(o2)) {
            throw e;
        }
    }

    public void notEquals(Object o1, Object o2, BlogException e) {
        if (o1.equals(o2)) {
            throw e;
        }
    }
}
