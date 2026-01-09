package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeLoginService;
import com.example.demo.dto.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(
		origins= { "http://127.0.0.1:5500",
		        "http://localhost:5500",
		        "http://localhost:3000",
		        "http://localhost:8080"},
				allowCredentials="true")
public class EmployeeLoginController {

    @Autowired
    private EmployeeLoginService EmployeeService;

    // 註冊
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee Employee) {
        try {
            Employee created = EmployeeService.register(Employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // 登入
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee Employee) {
        try {
            Employee loggedIn = EmployeeService.login(Employee.getUsername(), Employee.getPassword());
            return ResponseEntity.ok(loggedIn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // 變更密碼
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            Employee updated = EmployeeService.changePassword(
                    request.getUsername(),
                    request.getOldPassword(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
