package com.edunge.hospitalMgmt.response;

public class ErrorResponse extends BaseResponse{
        public ErrorResponse(String code, String message) {
            super(code,message);
        }
}
