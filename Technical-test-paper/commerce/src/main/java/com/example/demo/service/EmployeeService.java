package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.Member;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    // 註冊
    public Employee register(Employee employee) {
        if (employeeRepository.existsByUsername(employee.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 自動生成 employeeId
        Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByEmployeeIdDesc();
        String lastemployeeId = lastEmployee.map(Employee::getEmployeeId).orElse("E000");
        int nextNo = Integer.parseInt(lastemployeeId.substring(1)) + 1;
        employee.setEmployeeId(String.format("E%04d", nextNo));



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
    
    public Optional<Employee> getByemployeeId(String employeeId) { 
        return employeeRepository.findByEmployeeId(employeeId);
    }
    
    /** 前端只需要 employeeId + username 就好 */
    public Optional<EmployeeDto> getSimpleByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId)
                   .map(m -> new EmployeeDto(m.getEmployeeId(), m.getUsername()));
    }

    public record EmployeeDto(String employeeId, String username) {}
    
    
    /** 列出所有會員 */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /** 修改會員資料 */
    public Optional<Employee> updateEmployee(Long id, Member updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
        	employee.setUsername(updatedEmployee.getUsername());
        	employee.setPassword(updatedEmployee.getPassword());
            employee.setName(updatedEmployee.getName());
            return employeeRepository.save(employee);
        });
    }

    /** 刪除會員 */
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
        	employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
    
        
    

}
