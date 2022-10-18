package fun.ciallo.blog.dto;

import fun.ciallo.blog.entity.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * A DTO for the {@link Comment} entity <br/>
 * 用户评论用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CommentPostDto {
    @NotEmpty
    private String content;
    @NotNull
    private Long articleId;

    private Long target;
    @Null
    private Long userId;
}
