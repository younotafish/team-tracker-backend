package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
  User findByUserName(String userName);


}
