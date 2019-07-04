package com.stackroute.service;

import com.stackroute.exceptions.UserAllReadyExistException;
import com.stackroute.repository.UserRepository;
import com.stackroute.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    User user;
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    @Autowired
    KafkaTemplate<User,User> kafkaTemplate;

    private static String topic= "saveUser";

    @Override
    public User saveUser(User user) throws UserAllReadyExistException {
        if(userRepository.existsById(user.getEmailId()))
        {
            throw new UserAllReadyExistException("User already exist!!!!");
        }
        User saveUser= (User) userRepository.save(user);
        if(saveUser==null)
        {
            throw new UserAllReadyExistException("User already exist!!!");
        }
        kafkaTemplate.send(topic,saveUser);
        return saveUser;
    }

    @Override
    public List<User> getAllUsers() {

       List<User> userList=userRepository.findAll();
        return userList;
    }

    @Override
    public User findByEmailId(String emailId) throws Exception {
        user = null;
        Optional optional = userRepository.findById(emailId);
        if (optional.isPresent()) {
            user = userRepository.findById(emailId).get();
            return user;
        }
        else {
            throw new Exception("emailId does not exists");
        }
     }


    @Override
    public User deleteUser(String emailId) {
        user = null;
        Optional optional = userRepository.findById(emailId);
        if (optional.isPresent()) {
            user = userRepository.findById(emailId).get();
            userRepository.deleteById(emailId);
        }
        return user;
    }

    @Override
    public User updateUser( User user) {
        if (userRepository.existsById(user.getEmailId())) {
           // user.setId(user.getId());
            user.setName(user.getName());
            user.setGender(user.getGender());
            user.setEmailId(user.getEmailId());
            user.setMobileNo(user.getMobileNo());
        }
        user=userRepository.save(user);
        return user;
    }
}
