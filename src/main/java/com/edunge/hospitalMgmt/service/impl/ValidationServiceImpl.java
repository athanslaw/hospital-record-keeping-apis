package com.edunge.hospitalMgmt.service.impl;

import com.edunge.hospitalMgmt.exceptions.AuthException;
import com.edunge.hospitalMgmt.model.User;
import com.edunge.hospitalMgmt.repository.UserRepository;
import com.edunge.hospitalMgmt.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;

    @Autowired
    public ValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateUuid(String uuid) throws AuthException {
        User user = userRepository.findByUuid(uuid);
        if(user == null){
            throw new AuthException("Validation failed, the uuid passed does not exist in the database");
        }
    }

}
