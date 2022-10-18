package fun.ciallo.blog.service;

import fun.ciallo.blog.dto.ArticlePostDto;
import fun.ciallo.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public interface IArticleService {
    Article getById(Long id);

    Article postArticle(ArticlePostDto dto) throws IOException;

    Page<Article> getByPage(PageRequest pageRequest);

    Iterator<Article> getCarousel();
}
