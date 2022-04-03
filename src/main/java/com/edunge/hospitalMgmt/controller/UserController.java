package com.edunge.hospitalMgmt.controller;

import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.AuthException;
import com.edunge.hospitalMgmt.exceptions.BadRequestException;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.exceptions.UserException;
import com.edunge.hospitalMgmt.response.UserResponse;
import com.edunge.hospitalMgmt.service.UserService;
import com.edunge.hospitalMgmt.service.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/users")
@Api(value="User Mgmt", description="New user registration endpoint.")
@CrossOrigin(maxAge = 3600)
public class UserController {
    private final UserService userService;
    @Autowired
    ValidationService validationService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try{
            return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestHeader("uuid") String uuid, @RequestBody UserDto userDto) throws ParseException, UserException, NotFoundException, IOException, AuthException {
        try {
            validationService.validateUuid(uuid);
            return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
        }catch (AuthException authException){
            return new ResponseEntity<>(authException.getMessage(), HttpStatus.FORBIDDEN);
        }catch (BadRequestException badRequestException){
            return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "This method fetches all registered users. This can only be accessed by users with administrative privilege.")
    public ResponseEntity<UserResponse> getUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllUser());
    }

}
