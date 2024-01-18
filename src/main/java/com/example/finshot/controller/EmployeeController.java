package com.example.finshot.controller;

import com.example.finshot.domain.Employee;
import com.example.finshot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String showEmployeeList(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list-employees";
    }

    @GetMapping("/{id}")
    public String showEmployeeDetails(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee-details";
        } else {
            return "redirect:/employees";  // 직원이 존재하지 않으면 목록으로 리다이렉트
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "create-employee";
    }

    @PostMapping
    public String createEmployee(Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("success", "직원이 성공적으로 등록되었습니다.");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "edit-employee";
        } else {
            return "redirect:/employees";  // 직원이 존재하지 않으면 목록으로 리다이렉트
        }
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Employee employeeDetails, RedirectAttributes redirectAttributes) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee != null) {
            employeeService.updateEmployee(employeeDetails);
            redirectAttributes.addFlashAttribute("success", "직원 정보가 업데이트 되었습니다.");
        }
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("success", "직원이 삭제되었습니다.");
        return "redirect:/employees";
    }

    // 검색 결과 페이지를 위한 엔드포인트
    @GetMapping("/search")
    public String searchEmployees(@RequestParam String field, @RequestParam String value, Model model) {
        List<Employee> employees = employeeService.searchEmployees(field, value);
        model.addAttribute("employees", employees);
        return "list-employees";
    }
}
