import React from 'react';
import '../Styles/About.css';

const About = () => {
  return (
    <div className="about-container">
      <h2>About IMCS Department System</h2>
      
      <p>
        The IMCS Department Management System is a platform designed to streamline and simplify the way departments and users interact within an organization.
      </p>

      <p>
        With role-based access, super admins can manage departments and admins, admins can view and manage users, and users can securely access department-related information.
      </p>

      <p>
        Our goal is to provide a user-friendly, efficient, and secure system to ensure smooth departmental operations.
      </p>

      <p>
        The system offers real-time access to department data, allowing administrators to monitor performance, manage resources, and make informed decisions. It eliminates paperwork and manual tracking, replacing it with a centralized digital solution.
      </p>

      <p>
        Key features include:
        <ul>
          <li>✔️ Role-based dashboards for Super Admin, Admin, and User</li>
          <li>✔️ Secure login and registration with authentication</li>
          <li>✔️ Department creation, update, and removal by Super Admin</li>
          <li>✔️ Admin-level user management and access control</li>
          <li>✔️ Personalized views for users to access their department info</li>
        </ul>
      </p>

      <p>
        IMCS is committed to continuously improving the system to meet organizational needs and ensure data security, performance, and scalability.
      </p>
    </div>
  );
};

export default About;
