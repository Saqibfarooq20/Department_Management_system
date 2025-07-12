package com.example.dept_mgmt.Controller;

import com.example.dept_mgmt.Model.Department;
import com.example.dept_mgmt.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDepartment(@PathVariable Long id) {
        service.setDepartmentStatus(id, true);
        return ResponseEntity.ok("Department activated");
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateDepartment(@PathVariable Long id) {
        service.setDepartmentStatus(id, false);
        return ResponseEntity.ok("Department deactivated");
    }

    @GetMapping("/active")
    public List<Department> getActiveDepartments() {
        return service.getAllActiveDepartments();
    }

    @GetMapping("/all")
    public List<Department> getAllDepartments() {
        return service.getAllDepartment();
    }

    @PostMapping("/add")
    public Department addDepartment(@RequestBody Department departmentModel) {
        return service.addDepartment(departmentModel);
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return service.getDepartment(id);
    }

    @PutMapping("/updateDepartment/{id}")
    public Department updateDepartment(@RequestBody Department updatedDept, @PathVariable Long id) {
        return service.updateDepartment(id, updatedDept);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        service.deleteDepartment(id);
        return "Department Deleted Successfully";
    }
}
