import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../Styles/Login.css";
import axios from "axios";

const Login = ({ setIsLogin }) => {
  const [email, setemail] = useState("");
  const [password, setpassword] = useState("");
  const [user, setuser] = useState("SUPER_ADMIN"); // default role
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  console.log("📨 Submitting login with:", { email, password });

  try {
    const response = await axios.post("http://localhost:9090/api/login", {
      email,
      password,
    });

    console.log("✅ Server response:", response);

    if (response.data.token) {
      console.log("🟢 Login success. Saving to localStorage...");
      setIsLogin(true);
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role);
      localStorage.setItem("email", response.data.email);
      localStorage.setItem("userId", response.data.id)
      alert("Login successful!");

      const role = response.data.role;

      if (role === "SUPER_ADMIN") {
        navigate("/superadmin");
      } else if (role === "ADMIN") {
        navigate("/admin");
      } else if (role === "USER") {
        navigate("/user");
      }
    } else {
      console.warn("⚠️ Invalid credentials or missing token in response");
      alert("Invalid Email or Password");
    }
  } catch (error) {
    console.error("❌ Login failed:", error);
    if (error.code === "ERR_NETWORK") {
      alert("🚫 Cannot connect to the server. Is your backend running on port 9090?");
    } else {
      alert("Login failed: " + (error.response?.data?.error || "Unknown error"));
    }
  } finally {
    setLoading(false);
  }
};


  return (
    <div className="login-container">
      <form className="login-box" onSubmit={handleSubmit}>
        <h2>Login</h2>

        <label>Email</label>
        <input
          type="email"
          name="email"
          value={email}
          onChange={(e) => setemail(e.target.value)}
          required
        />

        <label>Password</label>
        <input
          type="password"
          name="password"
          value={password}
          onChange={(e) => setpassword(e.target.value)}
          required
        />

        <label>Role</label>
        <select
          name="role"
          value={user}
          onChange={(e) => setuser(e.target.value)}
        >
          <option value="SUPER_ADMIN">Super Admin</option>
          <option value="ADMIN">Admin</option>
          <option value="USER">User</option>
        </select>

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>
    </div>
  );
};

export default Login;
