package com.example.dept_mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.dept_mgmt.Model")

public class DeptMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeptMgmtApplication.class, args);
	}

}
