package com.example.dept_mgmt.Controller;

import com.example.dept_mgmt.Model.UserModel;
import com.example.dept_mgmt.Service.UserService;
import com.example.dept_mgmt.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Login - returns token, role, email, id
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserModel userModel) {
        System.out.println("📩 Received login request: " + userModel.getEmail());

        try {
            UserModel loggerCheck = service.login(userModel.getEmail(), userModel.getPassword());

            if (loggerCheck != null) {
                String token = jwtUtil.generateToken(loggerCheck.getEmail(), loggerCheck.getRole());
                System.out.println("✅ Token generated. Login success.");

                return Map.of(
                        "token", token,
                        "role", loggerCheck.getRole(),
                        "email", loggerCheck.getEmail(),
                        "id", String.valueOf(loggerCheck.getId())
                );
            }

            System.out.println("❌ Login failed: Invalid credentials");
            return Map.of("error", "Invalid Email or Password");

        } catch (Exception e) {
            System.out.println("❌ Login failed: Unknown error - " + e.getMessage());
            e.printStackTrace();
            return Map.of("error", "Unknown error");
        }
    }

    // ✅ Get users based on role
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<UserModel> getUsers(Authentication authentication) {
        UserModel currentUser = service.findByEmail(authentication.getName());

        if (currentUser.getRole().equals("ADMIN")) {
            Long deptId = currentUser.getDepartment().getId();
            return service.getUsersByRoleAndDepartment("USER", deptId);
        } else if (currentUser.getRole().equals("SUPER_ADMIN")) {
            return service.getAllUsers();
        }

        return List.of(); // For safety, return empty list otherwise
    }

    // ✅ Get user by ID
    @GetMapping("/users/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    // ✅ Add user
    @PostMapping("/addUser")
    public UserModel addUser(@RequestBody UserModel userModel) {
        return service.addUser(userModel);
    }

    // ✅ Update user
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PutMapping("/updateUser/{id}")
    public UserModel updateUser(@RequestBody UserModel userModel, @PathVariable Long id) {
        return service.updateUser(id, userModel);
    }

    // ✅ Delete user
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User Deleted Successfully";
    }

    // ✅ Activate user
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {
        service.setUserStatus(id, true);
        return ResponseEntity.ok("User activated");
    }

    // ✅ Deactivate user
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        service.setUserStatus(id, false);
        return ResponseEntity.ok("User deactivated");
    }

    // ✅ Get all active users (regardless of department)
    @GetMapping("/active")
    public List<UserModel> getActiveUsers() {
        return service.getAllActiveUsers();
    }
}
