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
    public UserModel updateUser(UserModel userModel, Long id) {
        UserModel existUser = userRepository.findById(id).orElse(null);
        if (existUser != null) {
            existUser.setName(userModel.getName());
            existUser.setDepartment(userModel.getDepartment());
            existUser.setPassword(passwordEncoder.encode(userModel.getPassword())); // encrypt new password
            return userRepository.save(existUser);
        }
        return null;
    }

    // ✅ Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
