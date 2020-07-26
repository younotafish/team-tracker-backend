package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
  User findByUserName(String userName);


}
