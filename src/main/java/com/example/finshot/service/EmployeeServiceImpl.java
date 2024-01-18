package com.example.finshot.service;

import com.example.finshot.domain.Employee;
import com.example.finshot.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        // 직원 번호를 3자리 형식으로 포맷팅
        String formattedNumber = String.format("%03d", Integer.parseInt(employee.getEmployeeNumber()));
        employee.setEmployeeNumber(formattedNumber);

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findById(employee.getId());
        if (employeeData.isPresent()) {
            Employee updatedEmployee = employeeData.get();
            updatedEmployee.setEmployeeNumber(employee.getEmployeeNumber());
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setPhoneNumber(employee.getPhoneNumber());
            updatedEmployee.setPosition(employee.getPosition());
            updatedEmployee.setEmail(employee.getEmail());
            return employeeRepository.save(updatedEmployee);
        } else {
            return null;
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String field, String value) {
        switch (field.toLowerCase()) {
            case "name":
                return employeeRepository.findByName(value);
            case "position":
                return employeeRepository.findByPosition(value);
            case "email":
                Employee employeeByEmail = employeeRepository.findByEmail(value);
                return employeeByEmail != null ? List.of(employeeByEmail) : List.of();
            case "phone_number":
                Employee employeeByPhoneNumber = employeeRepository.findByPhoneNumber(value);
                return employeeByPhoneNumber != null ? List.of(employeeByPhoneNumber) : List.of();
            case "employee_number":
                Employee employeeByNumber = employeeRepository.findByEmployeeNumber(value);
                return employeeByNumber != null ? List.of(employeeByNumber) : List.of();
            default:
                return List.of();
        }
    }
}
