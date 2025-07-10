import React from 'react';
import { Link } from 'react-router-dom';
import { FaUsers, FaUniversity } from 'react-icons/fa';
import '../../Styles/AdminDashboard.css';

const AdminDashboard = () => {
  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Welcome to Admin Dashboard!!</h2>
      <p>you have the access to view all the departments and manage users..</p>

      <div className="dashboard-links">
        <Link to="/admin/view-departments" className="dashboard-card">
          <FaUniversity className="dashboard-icon" />
          <h3>View Departments</h3>
          <p>See departments you belong to</p>
        </Link>

        <Link to="/admin/manage-users" className="dashboard-card">
          <FaUsers className="dashboard-icon" />
          <h3>Manage Users</h3>
          <p>Manage users in your department</p>
        </Link>
      </div>
    </div>
  );
};

export default AdminDashboard;
