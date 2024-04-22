package com.example.finalProjectDesignPatterns.repository;


import com.example.finalProjectDesignPatterns.entity.User;

public interface UserRepository extends BaseRepository<User> {

    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhone(String phone);
}
