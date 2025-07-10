import React from 'react';
import { Link } from 'react-router-dom';
import { FaUsers, FaBuilding } from 'react-icons/fa';
import '../../Styles/SuperAdminDashboard.css';

const SuperAdminDashboard = () => {
  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Welcome to Super Admin Dashboard!!</h2>
      <p>you have access for everything..you can manage users,departments,manage users view admin etc..</p>

      <div className="dashboard-links">
        <Link to="/superadmin/manage-departments" className="dashboard-card">
          <FaBuilding className="dashboard-icon" />
          <h3>Manage Departments</h3>
          <p>Create and organize departments</p>
        </Link>

        <Link to="/superadmin/manage-users" className="dashboard-card">
          <FaUsers className="dashboard-icon" />
          <h3>Manage Users</h3>
          <p>Add, remove, and manage users</p>
        </Link>
      </div>
    </div>
  );
};

export default SuperAdminDashboard;
