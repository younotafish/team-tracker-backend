package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.exceptions.UserNameException;
import com.teamtracker.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User findByUserName(String userName){
    User user = userRepository.findByUserName(userName);
    if(user == null){
      throw new UserNameException("User with name: "+ userName+" does not exist");
    }
    return user;
  }
}
