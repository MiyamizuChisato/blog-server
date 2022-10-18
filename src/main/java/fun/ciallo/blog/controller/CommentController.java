package fun.ciallo.blog.controller;

import fun.ciallo.blog.common.annotations.UseToken;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.dto.CommentPostDto;
import fun.ciallo.blog.entity.Article;
import fun.ciallo.blog.entity.Comment;
import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.service.ICommentService;
import fun.ciallo.blog.utils.AssertUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @Resource
    private AssertUtils assertUtils;

    @GetMapping("/sub/id/{id}")
    public List<Comment> getSubComments(@PathVariable Long id) {
        assertUtils.isTrue(id > 0, new BlogException(Status.PATH_ERROR));
        return commentService.getSubComments(id);
    }

    @UseToken
    @PostMapping("/post")
    public void postComment(@RequestBody CommentPostDto dto, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("id");
        dto.setUserId(id);
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        User user = new User();
        user.setId(dto.getUserId());
        comment.setUser(user);
        comment.setTarget(dto.getTarget());
        if (null == dto.getTarget()) {
            Article article = new Article();
            article.setId(dto.getArticleId());
            comment.setArticle(article);
            commentService.addComment(comment);
        } else {
            commentService.addSubComment(comment);
        }
    }
}
