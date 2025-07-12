package com.example.dept_mgmt.Repository;

import com.example.dept_mgmt.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);

    List<UserModel> findByActiveTrue();

    // ✅ Add these methods
    List<UserModel> findByRole(String role);

    List<UserModel> findByRoleAndDepartmentId(String role, Long departmentId);
}
