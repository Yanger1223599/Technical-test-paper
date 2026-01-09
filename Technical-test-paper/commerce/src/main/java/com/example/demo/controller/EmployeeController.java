package com.example.demo.controller;

import com.example.demo.dto.ChangePasswordRequest;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeService.EmployeeDto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(
    origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://localhost:3000",
        "http://localhost:8080"
    },
    allowCredentials = "true"
)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    /** 註冊 */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        try {
            Employee created = employeeService.register(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /** 登入 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee) {
        try {
            Employee loggedIn = employeeService.login(
                employee.getUsername(),
                employee.getPassword()
            );
            return ResponseEntity.ok(loggedIn);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /** 變更密碼 */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            Employee updated = employeeService.changePassword(
                request.getUsername(),
                request.getOldPassword(),
                request.getNewPassword()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 列出所有員工 */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /** 新增員工 */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    /** 修改員工 */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee updatedEmployee) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmployeeId(updatedEmployee.getEmployeeId());
            employee.setUsername(updatedEmployee.getUsername());
            employee.setPassword(updatedEmployee.getPassword());
            employee.setName(updatedEmployee.getName());

            employeeRepository.save(employee);
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** 刪除員工 */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok("員工刪除成功");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** 依 employeeId 查詢（DTO） */
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getByEmployeeId(@PathVariable String employeeId) {
        return employeeService.getSimpleByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** 兼容前端另一條 */
    @GetMapping("/employeeId/{employeeId}")
    public ResponseEntity<EmployeeDto> getByEmployeeIdAlt(@PathVariable String employeeId) {
        return getByEmployeeId(employeeId);
    }
}

