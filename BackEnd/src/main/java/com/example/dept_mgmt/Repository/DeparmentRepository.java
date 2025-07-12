package com.example.dept_mgmt.Repository;

import com.example.dept_mgmt.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeparmentRepository extends JpaRepository<Department,Long> {

    List<Department> findByActiveTrue();

}
