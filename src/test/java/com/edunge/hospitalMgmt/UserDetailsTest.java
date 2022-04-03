package com.edunge.hospitalMgmt;

import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.BadRequestException;
import com.edunge.hospitalMgmt.response.PatientResponse;
import com.edunge.hospitalMgmt.response.UserResponse;
import com.edunge.hospitalMgmt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes= HospitalMgmtApplication.class)
@ActiveProfiles("local")
public class UserDetailsTest {

    private static final UserDto userDto = new UserDto();
    @Autowired
    UserService userService;

    @Test
    public void registerStaff() throws BadRequestException {
        userDto.setName("Athanasius Samuel");
        UserResponse patients = userService.saveUser(userDto);
        assert(patients.getStatusCode().equals("00"));
    }

}
