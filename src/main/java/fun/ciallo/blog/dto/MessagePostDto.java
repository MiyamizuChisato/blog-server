package fun.ciallo.blog.dto;

import fun.ciallo.blog.entity.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * A DTO for the {@link Message} entity <br/>
 * 用户留言用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class MessagePostDto {
    @NotEmpty
    private String content;

    private Long target;
    @Null
    private Long userId;
}
