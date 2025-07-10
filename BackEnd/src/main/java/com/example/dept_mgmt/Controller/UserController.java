package com.example.dept_mgmt.Controller;

import com.example.dept_mgmt.Model.UserModel;
import com.example.dept_mgmt.Service.UserService;
import com.example.dept_mgmt.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    //sjgdsjhd
    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Login and return JWT Token


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
                        "id", String.valueOf(loggerCheck.getId()) // 👈 this is required
                );
            }

            System.out.println("❌ Login failed: Invalid credentials");
            return Map.of("error", "Invalid Email or Password");

        } catch (Exception e) {
            System.out.println("❌ Login failed: Unknown error - " + e.getMessage());
            e.printStackTrace(); // <-- important to see full error in console
            return Map.of("error", "Unknown error");
        }
    }



    // ✅ Get all users (secured with JWT)
    @GetMapping("/users")
    public List<UserModel> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserModel getUSerById (@PathVariable Long id)
    {
        return service.getUserById(id);
    }
    // ✅ Register user (public)
    @PostMapping("/addUser")
    public UserModel addUser(@RequestBody UserModel userModel) {
        return service.addUser(userModel);
    }

    // ✅ Update user (secured with JWT)
    @PutMapping("/update/{id}")
    public UserModel updateUser(@RequestBody UserModel userModel, @PathVariable Long id) {
        return service.updateUser(userModel, id);
    }

    // ✅ Delete user (secured with JWT)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User Deleted Successfully";
    }
}
