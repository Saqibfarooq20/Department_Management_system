import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../../Styles/ViewDepartment.css';

const ViewDepartments = () => {
  const [departments, setDepartments] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await axios.get("http://localhost:9090/api/department/all", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setDepartments(response.data);
      } catch (error) {
        console.error("Failed to fetch departments", error);
      }
    };

    fetchDepartments();
  }, []);

  return (
    <div className="view-departments-container">
      <h2>View Departments (Admin)</h2>
      <table className="departments-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Department Name</th>
            <th>HOD</th>
            <th>Users</th>
          </tr>
        </thead>
        <tbody>
          {departments.map((dept) => {
            // ✅ Find the first user with role "ADMIN"
            const hod = dept.users?.find(user => user.role === "ADMIN");

            return (
              <tr key={dept.id}>
                <td>{dept.id}</td>
                <td>{dept.name}</td>
                <td>{hod ? hod.name : "N/A"}</td>
                <td>{dept.users?.length || 0}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default ViewDepartments;
