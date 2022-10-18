package fun.ciallo.blog.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Regexp {
    String EMAIL = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
    String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9a-zA-Z]{8,16}$";

    String NICKNAME = "^[a-zA-Z\\u4e00-\\u9fa5\\u0800-\\u4e00]{2,12}$";

    String MOTTO = "^.{0,60}$";
}
