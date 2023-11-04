package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.comment.Comment;

public class CommentDao extends AbstractDao<Comment> {

    public CommentDao() {
        super(Comment.class);
    }
}
