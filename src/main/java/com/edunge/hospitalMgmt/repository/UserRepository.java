package com.edunge.hospitalMgmt.repository;

import com.edunge.hospitalMgmt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUuid(String uuid);
}
