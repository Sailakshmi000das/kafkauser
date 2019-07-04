package com.stackroute.service;

import com.stackroute.domain.User;
import com.stackroute.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException; //Added new viewer

    User findByEmailIdAndPassword(String emailId, String password); //find viewer by id and password
}
