package com.example.dept_mgmt.Controller;

import com.example.dept_mgmt.Model.Department;
import com.example.dept_mgmt.Model.UserModel;
import com.example.dept_mgmt.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "http://localhost:5173")

public class DepartmentController {

    @Autowired
    private DepartmentService service;


    @GetMapping("/all")
    public List<Department> getAllDepartments() {
        return service.getAllDepartment();
    }

    @PostMapping("/add")
    public Department addDepartment(@RequestBody Department departmentModel) {
        return service.addDepartment(departmentModel);
    }

    //  Get department by ID
//    @GetMapping("/{id}")
//    public Department getDepartmentById(@PathVariable Long id) {
//        return service.de(id);
//    }

    //  Update department
//    @PutMapping("/update/{id}")
//    public Department updateDepartment(@RequestBody Department departmentModel, @PathVariable Long id) {
//        return service.(departmentModel, id);
//    }

    //Delete department
    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        service.deleteDepartment(id);
        return "Department Deleted Successfully";
    }
}
