package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.post.Post;

public class PostDao extends AbstractDao<Post> {

    public PostDao() {
        super(Post.class);
    }
}
