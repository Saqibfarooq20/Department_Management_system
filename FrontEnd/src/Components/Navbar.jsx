import React from "react";

// ====================change======================
import { Link, useNavigate } from "react-router-dom";
import "../Styles/Navbar.css";
import logo from "../assets/images.png";

const Navbar = ({ login, setIsLogin }) => {
  console.log(login)
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("email");
    setIsLogin(false); // update state
    navigate("/login"); // redirect to login
  };
  let role = localStorage.getItem("role")
 
  return (
    <nav className="navbar">
      <div className="logo-section">
        <img src={logo} alt="IMCS Logo" className="logo" />
        <h1>IMCS</h1>
      </div>
      <ul className="nav-links">
  <li><Link to="/">Home</Link></li>
  <li><Link to="/about">About Us</Link></li>
  <li><Link to="/contact">Contact Us</Link></li>

  {role === "ADMIN" && (
    <li><Link to="/admin">Admin Dashboard</Link></li>
  )}

  {role === "SUPER_ADMIN" && (
    <li><Link to="/superadmin">Super Admin hmari deepanshi</Link></li>
  )}

  {!login && (
    <li><Link to="/login">Login</Link></li>
  )}

  {login && (
    <li><Link to="/" onClick={handleLogout}>Logout</Link></li>
  )}
   {role === "USER" && (
    <li><Link to="/user">User</Link></li>
  )}
</ul>

    </nav>
  );
};

export default Navbar;
