import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../Styles/ManageUsers.css";

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [newUser, setNewUser] = useState({ name: "", email: "", password: "" });
  const [loading, setLoading] = useState(true);
  const [adminDeptId, setAdminDeptId] = useState(null);
  const [adminDeptName, setAdminDeptName] = useState("");
  const token = localStorage.getItem("token");
  const email = localStorage.getItem("email");

  useEffect(() => {
    const fetchAdminInfo = async () => {
      try {
        const res = await axios.get(`http://localhost:9090/api/users/me?email=${email}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        const dept = res.data?.department;
        setAdminDeptId(dept?.id);
        setAdminDeptName(dept?.name);
        fetchUsers(dept?.id);
      } catch (err) {
        console.error("❌ Error fetching admin info", err);
        setLoading(false);
      }
    };

    const fetchUsers = async (deptId) => {
      try {
        const res = await axios.get("http://localhost:9090/api/users", {
          headers: { Authorization: `Bearer ${token}` },
        });

        const deptUsers = res.data.filter(
          (user) => user.role === "USER" && user.department?.id === deptId
        );
        setUsers(deptUsers);
      } catch (err) {
        console.error("❌ Error fetching users", err);
      } finally {
        setLoading(false);
      }
    };

    fetchAdminInfo();
  }, []);

  const handleInput = (e) => {
    setNewUser({ ...newUser, [e.target.name]: e.target.value });
  };

  const handleAddUser = async () => {
    if (!newUser.name || !newUser.email || !newUser.password) {
      return alert("All fields are required");
    }

    try {
      await axios.post(
        "http://localhost:9090/api/addUser",
        {
          ...newUser,
          role: "USER",
          department: { id: adminDeptId },
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      alert("User created successfully");
      setNewUser({ name: "", email: "", password: "" });

      // Refresh list
      const res = await axios.get("http://localhost:9090/api/users", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const deptUsers = res.data.filter(
        (user) => user.role === "USER" && user.department?.id === adminDeptId
      );
      setUsers(deptUsers);
    } catch (err) {
      console.error("❌ Failed to add user", err);
      alert("Something went wrong");
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:9090/api/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsers((prev) => prev.filter((u) => u.id !== id));
      alert("User deleted successfully");
    } catch (err) {
      console.error("❌ Failed to delete user", err);
      alert("Failed to delete user");
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div className="manage-users-container">
      <h2>Manage Users</h2>
      <div style={{ display: "flex", gap: "10px", marginBottom: "20px" }}>
        <input
          type="text"
          name="name"
          value={newUser.name}
          placeholder="Enter user name"
          onChange={handleInput}
        />
        <input
          type="email"
          name="email"
          value={newUser.email}
          placeholder="Enter user email"
          onChange={handleInput}
        />
        <input
          type="password"
          name="password"
          value={newUser.password}
          placeholder="Enter password"
          onChange={handleInput}
        />
        <button onClick={handleAddUser}>Add User</button>
      </div>

      {users.length === 0 ? (
        <p>No users in your department ({adminDeptName})</p>
      ) : (
        <table className="users-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Department</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.name}</td>
                <td>{u.email}</td>
                <td>{u.role}</td>
                <td>{u.department?.name}</td>
                <td>
                  <button
                    onClick={() => handleDelete(u.id)}
                    style={{ background: "red", color: "white" }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ManageUsers;
