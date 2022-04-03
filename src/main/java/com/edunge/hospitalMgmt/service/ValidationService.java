package com.edunge.hospitalMgmt.service;

import com.edunge.hospitalMgmt.exceptions.AuthException;

public interface ValidationService {
    void validateUuid(String uuid) throws AuthException;
}
