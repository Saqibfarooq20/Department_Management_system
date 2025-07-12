package com.example.dept_mgmt.Service;

import com.example.dept_mgmt.Model.UserModel;
import com.example.dept_mgmt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Login with encrypted password check
    public UserModel login(String email, String password) {
        System.out.println("🔐 Attempting login for email: " + email);

        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("❌ No user found with email: " + email);
            return null;
        }

        System.out.println("✅ User found: " + user.getName());
        System.out.println("🔍 Stored password (hashed): " + user.getPassword());
        boolean match = passwordEncoder.matches(password, user.getPassword());
        System.out.println("🧪 Password match: " + match);

        if (match) {
            return user;
        } else {
            System.out.println("❌ Password mismatch for email: " + email);
            return null;
        }
    }


    // ✅ Get all users
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Add user with encrypted password
    public UserModel addUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    // ✅ Get by ID
    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // ✅ Update user (encrypt new password)
    public UserModel updateUser(Long id, UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setActive(updatedUser.isActive());
        existingUser.setDepartment(updatedUser.getDepartment());

        // 👇 Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }


    public void setUserStatus(Long id, boolean status) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(status);
        userRepository.save(user);
    }
    public List<UserModel> getAllActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    // ✅ Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserModel> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<UserModel> getUsersByRoleAndDepartment(String role, Long deptId) {
        return userRepository.findByRoleAndDepartmentId(role, deptId);
    }
}
