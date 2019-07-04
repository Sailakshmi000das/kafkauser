package com.stackroute.controller;

import com.stackroute.exceptions.UserAllReadyExistException;
import com.stackroute.domain.User;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("user")
    public ResponseEntity<?> saveUser(@RequestBody User user)
    {
        ResponseEntity responseEntity;
        try {
            userService.saveUser(user);
            responseEntity=new ResponseEntity<String>("Successfully created", HttpStatus.CREATED);
        }
        catch (UserAllReadyExistException e){
            responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
    }
    @DeleteMapping("user/{emailId}")
    public ResponseEntity<?> deleteUser(@PathVariable String emailId)
    {
        ResponseEntity responseEntity;
        userService.deleteUser(emailId);
        responseEntity=new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
        return responseEntity;
    }
    @PutMapping(value="/user/{emailId}")
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        ResponseEntity responseEntity;
        userService.updateUser(user);
        responseEntity = new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
        return responseEntity;
    }
    @GetMapping("user/{emailId}")
    public ResponseEntity<?> FindByEmail(@PathVariable String emailId) throws Exception
    {
        ResponseEntity responseEntity;
        userService.findByEmailId(emailId);
        responseEntity=new ResponseEntity<String>("fetched Successfully", HttpStatus.FOUND);
        return responseEntity;
    }

}
