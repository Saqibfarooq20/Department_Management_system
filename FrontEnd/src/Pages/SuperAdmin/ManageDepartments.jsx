import React, { useState, useEffect } from 'react';
import '../../Styles/ManageDepartments.css';
import axios from 'axios';

const ManageDepartments = () => {
  const [departmentName, setdepartmentName] = useState('');
  const [departments, setDepartments] = useState([]);

  // ✅ Fetch all departments
  const fetchDepartments = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:9090/api/department/all", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setDepartments(response.data);
    } catch (error) {
      console.error("Error fetching departments:", error);
      alert("Failed to fetch departments.");
    }
  };

  // ✅ Fetch on component mount
  useEffect(() => {
    fetchDepartments();
  }, []);

  // ✅ Add department
  const handleAddDepartment = async () => {
    if (!departmentName.trim()) {
      alert("Please enter a department name.");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      await axios.post(
        "http://localhost:9090/api/department/add",
        { name: departmentName },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Department added successfully!");
      setdepartmentName("");
      fetchDepartments(); // refresh list
    } catch (error) {
      console.error("Error adding department:", error);
      alert("Failed to add department.");
    }
  };

  // ✅ Delete department
  const handleDeleteDepartment = async (id) => {
    if (!window.confirm("Are you sure you want to delete this department?")) return;

    try {
      const token = localStorage.getItem("token");
      await axios.delete(`http://localhost:9090/api/department/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert("Department deleted successfully!");
      fetchDepartments(); // refresh list
    } catch (error) {
      console.error("Error deleting department:", error);
      alert("Failed to delete department.");
    }
  };

  return (
    <div className="manage-departments-container">
      <h2>Manage Departments</h2>

      <div className="add-department">
        <input
          type="text"
          placeholder="Enter department name"
          value={departmentName}
          onChange={(e) => setdepartmentName(e.target.value)}
        />
        <button onClick={handleAddDepartment}>Add Department</button>
      </div>

      <table className="department-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Department Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {departments.length > 0 ? (
            departments.map((dept) => (
              <tr key={dept.id}>
                <td>{dept.id}</td>
                <td>{dept.name}</td>
                <td>
                  <button onClick={() => handleDeleteDepartment(dept.id)}>Delete</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">No departments available</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ManageDepartments;
