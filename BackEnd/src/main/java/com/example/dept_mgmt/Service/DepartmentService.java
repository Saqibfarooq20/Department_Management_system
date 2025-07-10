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

    public List<Department> getAllDepartment (){
        return departmentRepository.findAll();
    }

    public Department getDepartment (Long id)
    {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department addDepartment (Department department)
    {
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }


}
