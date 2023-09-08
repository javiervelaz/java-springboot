package com.globalLogic.usermircroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globalLogic.usermircroservice.model.User;

@Repository
public interface  UserRepository  extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
