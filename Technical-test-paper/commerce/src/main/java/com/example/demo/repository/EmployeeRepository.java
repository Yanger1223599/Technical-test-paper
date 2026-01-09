package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
    Optional<Employee> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmployeeId(String employeeId);

    // 找出最大 empNo 的會員（自動只取一筆）
    Optional<Employee> findTopByOrderByEmployeeIdDesc();
    
 
    

}
