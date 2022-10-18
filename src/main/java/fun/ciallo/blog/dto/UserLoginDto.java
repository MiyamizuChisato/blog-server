package fun.ciallo.blog.dto;

import fun.ciallo.blog.common.annotations.Regexp;
import fun.ciallo.blog.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity <br/>
 * 用户登录用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserLoginDto implements Serializable {
    @Pattern(regexp = Regexp.EMAIL)
    private String email;

    @Pattern(regexp = Regexp.PASSWORD)
    private String password;
}