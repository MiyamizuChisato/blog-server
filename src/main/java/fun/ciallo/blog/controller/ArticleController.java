package fun.ciallo.blog.controller;

import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.dto.ArticlePostDto;
import fun.ciallo.blog.entity.Article;
import fun.ciallo.blog.service.IArticleService;
import fun.ciallo.blog.utils.AssertUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Resource
    private IArticleService articleService;
    @Resource
    private AssertUtils assertUtils;

    @GetMapping("/id/{id}")
    public Article getById(@PathVariable Long id) {
        assertUtils.isTrue(id > 0, new BlogException(Status.PATH_ERROR));
        return articleService.getById(id);
    }

    @PostMapping("/post")
    public Article postArticle(@RequestParam("image") MultipartFile image,
                               @RequestParam("content") MultipartFile content,
                               @RequestParam("title") String title) throws IOException {
        ArticlePostDto dto = new ArticlePostDto();
        dto.setContent(content);
        dto.setImage(image);
        dto.setTitle(title);
        return articleService.postArticle(dto);
    }

    @GetMapping("/page/{current}")
    public Page<Article> getByPage(@PathVariable @Validated @NotNull int current) {
        if (current < 0) {
            current = 0;
        }
        Sort sort = Sort.by("createTime").descending();
        PageRequest pageRequest = PageRequest.of(current, 5, sort);
        return articleService.getByPage(pageRequest);
    }

    @GetMapping("/carousel")
    public Iterator<Article> getCarousel() {
        return articleService.getCarousel();
    }
}
