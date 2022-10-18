package fun.ciallo.blog.dto;

import fun.ciallo.blog.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity <br/>
 * 用户注册用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserRegisterDto implements Serializable {
    private String code;
    private String nickname;
    private UserLoginDto userAccount;
}