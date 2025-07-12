package com.example.dept_mgmt.Service;

import com.example.dept_mgmt.Model.Department;
import com.example.dept_mgmt.Repository.DeparmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DeparmentRepository departmentRepository;

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department updateDepartment(Long id, Department updatedDept) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        department.setName(updatedDept.getName());
        department.setDescription(updatedDept.getDescription()); // ✅ Also update description

        return departmentRepository.save(department);
    }

    public void setDepartmentStatus(Long id, boolean status) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        dept.setActive(status);
        departmentRepository.save(dept);
    }

    public List<Department> getAllActiveDepartments() {
        return departmentRepository.findByActiveTrue();
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
