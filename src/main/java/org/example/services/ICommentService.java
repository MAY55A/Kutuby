package org.example.services;

import org.example.entities.Collection;
import org.example.entities.Comment;

import java.util.List;

public interface ICommentService {
    public List<Comment>  findAll();
    Comment findByIdComment(Integer id); // Updated to accept an Integer parameter
    public Comment addComment(Comment co);
    void deleteComment(Integer id); // Updated to accept an Integer parameter
    public Comment updateComment(Integer id , Comment co);
}
