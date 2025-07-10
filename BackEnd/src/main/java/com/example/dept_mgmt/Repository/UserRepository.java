package com.example.dept_mgmt.Repository;

import com.example.dept_mgmt.Model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    UserModel findByEmail(String email);  // yahan bhi UserModel likhna hai

}
