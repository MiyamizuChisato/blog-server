package fun.ciallo.blog.service.impl;

import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.entity.Comment;
import fun.ciallo.blog.repositories.ArticleRepository;
import fun.ciallo.blog.repositories.CommentRepository;
import fun.ciallo.blog.service.ICommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Resource
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getSubComments(Long id) {
        return commentRepository.findAllByTarget(id);
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void addSubComment(Comment comment) {
        Comment supComment = getById(comment.getTarget());
        supComment.setChildren(supComment.getChildren() + 1);
        addComment(comment);
    }

    @Override
    public Comment getById(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BlogException(Status.PATH_ERROR);
    }
}
