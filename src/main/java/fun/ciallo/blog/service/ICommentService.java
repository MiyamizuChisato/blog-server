package fun.ciallo.blog.service;

import fun.ciallo.blog.entity.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getSubComments(Long id);

    void addComment(Comment comment);

    void addSubComment(Comment comment);

    Comment getById(Long id);
}
