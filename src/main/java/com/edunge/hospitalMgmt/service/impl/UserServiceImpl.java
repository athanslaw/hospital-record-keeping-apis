package com.edunge.hospitalMgmt.service.impl;

import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.AuthException;
import com.edunge.hospitalMgmt.exceptions.BadRequestException;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.model.*;
import com.edunge.hospitalMgmt.repository.UserRepository;
import com.edunge.hospitalMgmt.response.UserResponse;
import com.edunge.hospitalMgmt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String generateUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @Override
    public UserResponse saveUser(UserDto userDto) throws BadRequestException {
        if(userDto.getName() == null)
        {
            throw new BadRequestException("Kindly submit a valid name");
        }
            String uuid = this.generateUuid();
        User existingUser = userRepository.findByUuid(uuid);
        if(existingUser!=null){
            uuid = this.generateUuid();
        }
        userDto.setUuid(uuid);
        User user = new User();
        user = user.toUser(userDto);
        userRepository.save(user);
        return new UserResponse("00", "Staff Registered Successfully.",null, user);
    }

    @Override
    public UserResponse updateUser(UserDto userDto) throws NotFoundException, BadRequestException {
        if(userDto.getUuid() == null || userDto.getName() == null){
            throw new BadRequestException("UUID and Name are required");
        }

        User existingUser = userRepository.findByUuid(userDto.getUuid());
        if(existingUser ==null){
            throw new NotFoundException(String.format("%s does not exist.",userDto.getUuid()));
        }
        existingUser.setName(userDto.getName());
        existingUser.setUpdatedAt(Date.from(Instant.now()));

        userRepository.save(existingUser);
        return new UserResponse("00", "Staff Record Updated Successfully.",null, existingUser);
    }

    @Override
    public UserResponse getAllUser() throws NotFoundException {
        List<User> users = userRepository.findAll();
        return new UserResponse("00", "List of users", users);
    }

}
