package org.example.services;

import org.example.entities.Collection;
import org.example.entities.Comment;

import java.util.List;

public interface ICommentService {
    public List<Comment>  findAll();
    public Comment findByIdComment();







    public Comment addComment(Comment co);
    public void DeleteComment(Comment co);
    public Comment updateComment(Integer id , Comment co);
}
