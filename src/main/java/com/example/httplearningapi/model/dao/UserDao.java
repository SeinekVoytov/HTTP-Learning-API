package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.user.User;

public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }
}
