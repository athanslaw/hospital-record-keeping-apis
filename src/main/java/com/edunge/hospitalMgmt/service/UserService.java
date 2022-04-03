package com.edunge.hospitalMgmt.service;

import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.BadRequestException;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.response.UserResponse;

public interface UserService {
    UserResponse saveUser(UserDto userDto) throws BadRequestException;
    UserResponse updateUser(UserDto userDto) throws NotFoundException, BadRequestException;
    UserResponse getAllUser() throws NotFoundException;
}
