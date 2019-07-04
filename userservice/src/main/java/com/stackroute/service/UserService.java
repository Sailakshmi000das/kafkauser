package com.stackroute.service;

import com.stackroute.exceptions.UserAllReadyExistException;
import com.stackroute.domain.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user) throws UserAllReadyExistException;
    public List<User> getAllUsers();
    public User deleteUser(String emailId);
    public  User updateUser(User user);
    public User findByEmailId(String emailId) throws Exception;
}
