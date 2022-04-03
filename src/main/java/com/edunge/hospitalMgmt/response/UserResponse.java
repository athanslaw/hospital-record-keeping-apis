package com.edunge.hospitalMgmt.response;

import com.edunge.hospitalMgmt.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseResponse {
    private User user;
    private String token;
    public List<User> users;

    public UserResponse(String code, String message, String token) {
        super(code, message);
        this.token = token;
    }

    public UserResponse(String code, String message, String token, User user) {
        super(code,message);
        this.token = token;
        this.user = user;
    }

    public UserResponse(String code, String message, List<User> users) {
        super(code, message);
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
