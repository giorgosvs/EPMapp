package com.redux.services;

import java.util.List;

import com.redux.Entity.User;

public interface UserService {
	List <User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    void registerNewUserAccount(User user);
    boolean isEmailUnique(String email);
    User getUserByEmail(String email);
    boolean verifyCode(String verificationCode);
}
