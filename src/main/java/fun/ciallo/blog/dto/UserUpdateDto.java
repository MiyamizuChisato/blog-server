package fun.ciallo.blog.dto;

import fun.ciallo.blog.common.annotations.Regexp;
import fun.ciallo.blog.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity <br/>
 * 用户更新用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDto implements Serializable {
    @Pattern(regexp = Regexp.NICKNAME)
    private String nickname;
    @Pattern(regexp = Regexp.MOTTO)
    private String motto;
}