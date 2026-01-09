package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeLoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // 註冊
    public Employee register(Employee employee) {
        if (employeeRepository.existsByUsername(employee.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 自動生成 employeeId
        Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByEmployeeIdDesc();
        String lastemployeeId = lastEmployee.map(Employee::getEmployeeId).orElse("000000000");
        int nextNo = Integer.parseInt(lastemployeeId.substring(1)) + 1;
        employee.setEmployeeId(String.format("%04d", nextNo));



        return employeeRepository.save(employee);
    }

    // 登入
    public Employee login(String username, String password) {
        Optional<Employee> optEmployee = employeeRepository.findByUsername(username);
        if (optEmployee.isPresent()) {
        	Employee employee = optEmployee.get();



            if (employee.getPassword().equals(password)) {
                return employee;
            }
        }
        throw new RuntimeException("Invalid username or password");
    }

    // 變更密碼
    public Employee changePassword(String username, String oldPassword, String newPassword) {
        Optional<Employee> optEmployee = employeeRepository.findByUsername(username);
        if (optEmployee.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Employee employee = optEmployee.get();

        if (!employee.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }

        employee.setPassword(newPassword);
        return employeeRepository.save(employee);
    }
}
