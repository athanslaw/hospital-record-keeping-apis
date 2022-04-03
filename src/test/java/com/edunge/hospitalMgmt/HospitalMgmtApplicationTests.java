package com.edunge.hospitalMgmt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("local")
@Sql({"/employees_schema.sql", "/import_employees.sql"})
class HospitalMgmtApplicationTests {

	@Test
	void contextLoads() {
	}

}
