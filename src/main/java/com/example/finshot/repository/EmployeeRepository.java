package com.example.finshot.repository;

import com.example.finshot.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // 이름으로 직원 검색
    List<Employee> findByName(String name);
    // 직급으로 직원 검색
    List<Employee> findByPosition(String position);
    // 이메일로 직원 검색
    Employee findByEmail(String email);
    // 이름으로 정렬하여 모든 직원 조회
    List<Employee> findAllByOrderByNameAsc();
    // 직원 번호로 직원 검색
    Employee findByEmployeeNumber(String employeeNumber);

    Employee findByPhoneNumber(String phoneNumber);
}
