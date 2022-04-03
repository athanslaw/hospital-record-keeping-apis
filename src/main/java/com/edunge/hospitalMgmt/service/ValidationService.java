package com.edunge.hospitalMgmt.service;

import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.AuthException;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.response.UserResponse;

public interface ValidationService {
    void validateUuid(String uuid) throws AuthException;
}
