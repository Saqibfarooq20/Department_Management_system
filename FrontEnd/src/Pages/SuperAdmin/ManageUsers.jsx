import React, { useState, useEffect } from 'react';
import '../../Styles/ManageUsers.css';
import axios from 'axios';

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState(''); // ✅ New password state
  const [role, setRole] = useState('');
  const [departmentId, setDepartmentId] = useState('');

  const token = localStorage.getItem("token");

  const fetchUsers = async () => {
    try {
      const response = await axios.get("http://localhost:9090/api/users", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
      alert("Failed to fetch users.");
    }
  };

  const fetchDepartments = async () => {
    try {
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

  useEffect(() => {
    fetchUsers();
    fetchDepartments();
  }, []);

  const handleAddUser = async () => {
    if (!name || !email || !password || !role || !departmentId) {
      alert("Please fill all fields");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:9090/api/addUser",
        {
          name,
          email,
          password, // ✅ Use the password entered by user
          role,
          department: { id: departmentId }
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("User added successfully!");
      setName('');
      setEmail('');
      setPassword(''); // ✅ Clear the password field
      setRole('');
      setDepartmentId('');
      fetchUsers();
    } catch (error) {
      console.error("Error adding user:", error);
      alert("Failed to add user.");
    }
  };

  const handleDeleteUser = async (userId) => {
    try {
      await axios.delete(`http://localhost:9090/api/delete/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert("User deleted successfully!");
      fetchUsers();
    } catch (error) {
      console.error("Error deleting user:", error);
      alert("Failed to delete user.");
    }
  };

  return (
    <div className="manage-users-container">
      <h2>Manage Users</h2>

      <div className="add-user">
        <input
          type="text"
          placeholder="Enter user name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="email"
          placeholder="Enter user email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        /> {/* ✅ Password input */}
        <select value={role} onChange={(e) => setRole(e.target.value)}>
          <option value="">Select Role</option>
          <option value="ADMIN">Admin</option>
          <option value="USER">User</option>
        </select>
        <select value={departmentId} onChange={(e) => setDepartmentId(e.target.value)}>
          <option value="">Select Department</option>
          {departments.map((dept) => (
            <option key={dept.id} value={dept.id}>
              {dept.name}
            </option>
          ))}
        </select>
        <button onClick={handleAddUser}>Add User</button>
      </div>

      <table className="user-table">
        <tbody>
  {users.map((user) => (
    <tr key={user.id}>
      <td>{user.id}</td>
      <td>{user.name}</td>
      <td>{user.email}</td>
      <td>{user.role}</td>
      <td>{user.department?.name || "N/A"}</td>
      <td>
        {user.role !== "SUPER_ADMIN" && (
          <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
        )}
      </td>
    </tr>
  ))}
</tbody>
      </table>
    </div>
  );
};

export default ManageUsers;
