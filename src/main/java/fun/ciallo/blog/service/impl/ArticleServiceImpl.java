package fun.ciallo.blog.service.impl;

import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.dto.ArticlePostDto;
import fun.ciallo.blog.entity.Article;
import fun.ciallo.blog.repositories.ArticleRepository;
import fun.ciallo.blog.service.IArticleService;
import fun.ciallo.blog.utils.OssUtils;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private OssUtils ossUtils;

    @Override
    public Article getById(Long id) {
        Optional<Article> optional = articleRepository.findById(id);
        if (optional.isPresent()) {
            Article article = optional.get();
            article.setWatch(article.getWatch() + 1);
            return article;
        }
        throw new BlogException(Status.PATH_ERROR);
    }

    @Override
    public Article postArticle(ArticlePostDto dto) throws IOException {
        String image = ossUtils.uploadImage(dto.getImage());
        String content = ossUtils.uploadMarkdown(dto.getContent());
        Article article = new Article();
        article.setTitle(dto.title);
        article.setContent(content);
        article.setImage(image);
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> getByPage(PageRequest pageRequest) {
        return articleRepository.findAll(pageRequest);
    }

    @Override
    public Iterator<Article> getCarousel() {
        Sort sort = Sort.by("watch").descending();
        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        return articleRepository.findAll(pageRequest).iterator();
    }
}
