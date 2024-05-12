package org.example.services;

import org.example.entities.Comment;
import org.example.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findByIdComment(Integer id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.orElse(null);
    }

    public Comment addComment(Comment co) {
        return commentRepository.save(co);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(Integer id, Comment co) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment existingComment = commentOptional.get();
            existingComment.setContent(co.getContent());
            existingComment.setCreatedAt(co.getCreatedAt());
            existingComment.setUser(co.getUser());
            return commentRepository.save(existingComment);
        }
        return null;
    }
}
