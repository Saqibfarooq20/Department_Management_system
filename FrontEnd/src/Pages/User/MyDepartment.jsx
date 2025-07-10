import React from 'react';
import '../../Styles/MyDepartment.css';

const MyDepartment = () => {
  return (
    <div className="my-department-container">
      <h2>My Department</h2>

      <div className="department-card">
        <p><strong>Department Name:</strong> Computer Science</p>
        <p><strong>Department Code:</strong> CSE101</p>
        <p><strong>Head of Department:</strong> Dr. Anuj Sharma</p>
        <p><strong>Total Faculty:</strong> 12</p>
        <p><strong>Location:</strong> Block A, 2nd Floor</p>
      </div>
    </div>
  );
};

export default MyDepartment;
