import React from 'react';
import { Link } from 'react-router-dom';
import { FaInfoCircle, FaBuilding } from 'react-icons/fa';
import '../../Styles/UserDashboard.css';

const UserDashboard = () => {
  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Welcome to User Dashboard!!</h2>
      <p>you can view your department and your details only..</p>

      <div className="dashboard-links">
        <Link to="/user/my-department" className="dashboard-card">
          <FaBuilding className="dashboard-icon" />
          <h3>My Department</h3>
          <p>View your department details</p>
        </Link>

        <Link to="/user/my-details" className="dashboard-card">
          <FaInfoCircle className="dashboard-icon" />
          <h3>My Details</h3>
          <p>See your profile information</p>
        </Link>
      </div>
    </div>
  );
};

export default UserDashboard;
