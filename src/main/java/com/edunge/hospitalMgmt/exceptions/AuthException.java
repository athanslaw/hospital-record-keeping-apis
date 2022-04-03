package com.edunge.hospitalMgmt.exceptions;

public class AuthException extends BaseException {
    private static String CODE= "03";
    public AuthException(String message) {
        super(CODE, message);
    }
}
