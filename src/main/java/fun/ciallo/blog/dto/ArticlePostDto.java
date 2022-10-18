package fun.ciallo.blog.dto;

import fun.ciallo.blog.entity.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link Article} entity <br/>
 * 新增文章用DTO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticlePostDto {
    public String title;
    public MultipartFile image;
    public MultipartFile content;
}
