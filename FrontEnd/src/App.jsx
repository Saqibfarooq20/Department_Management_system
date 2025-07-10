import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import Home from "./Pages/Home";
import Login from "./Pages/Login";
import About from './Pages/About';
import Contact from './Pages/Contact';
import SuperAdminDashboard from './Pages/SuperAdmin/SuperAdminDashboard';
import ManageDepartments from './Pages/SuperAdmin/ManageDepartments';
import ManageUsers from './Pages/SuperAdmin/ManageUsers';
import AdminDashboard from './Pages/Admin/AdminDashboard';
import ViewDepartments from './Pages/Admin/ViewDepartments';
import ManageUser from './Pages/Admin/ManageUser';
import UserDashboard from './Pages/User/UserDashboard';
import MyDepartment from './Pages/User/MyDepartment';
import MyDetails from './Pages/User/MyDetails';


function App() {
  let [login,setIsLogin] = useState(false)
  return (
    <>
      <Navbar  login={login}/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login  setIsLogin={setIsLogin} />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
      
      <Route path="/superadmin" element={<SuperAdminDashboard />} />
<Route path="/superadmin/manage-departments" element={<ManageDepartments />} />
<Route path="/superadmin/manage-users" element={<ManageUsers />} />
<Route path="/admin" element={<AdminDashboard />} />
<Route path="/admin/view-departments" element={<ViewDepartments />} />
<Route path="/admin/manage-user" element={<ManageUser />} />

<Route path="/user" element={<UserDashboard />} />
<Route path="/user/my-department" element={<MyDepartment />} />
<Route path="/user/my-details" element={<MyDetails />} />
        <Route path="/admin/manage-users" element={<ManageUsers />} />



</Routes>
<Footer/>
    </>
    
  );
}

export default App;
