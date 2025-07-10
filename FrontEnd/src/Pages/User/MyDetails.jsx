import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../Styles/MyDetails.css";

const MyDetails = () => {
  const [user, setUser] = useState(null);
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId"); // ✅ FIXED

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:9090/api/users/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUser(response.data);
      } catch (error) {
        console.error("❌ Error fetching user details:", error);
      }
    };

    if (userId) fetchUserDetails();
  }, [userId]); // ✅ userId is now defined above

  if (!user) return <p>Loading...</p>;

  return (
    <div className="my-details-container">
      <h2>My Details</h2>
      <div className="details-card">
        <p><strong>Name:</strong> {user.name}</p>
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Role:</strong> {user.role}</p>
        <p><strong>Department:</strong> {user.department?.name || "N/A"}</p>
        <p><strong>Joined:</strong> {new Date(user.createdAt).toDateString()}</p>
      </div>
    </div>
  );
};

export default MyDetails;
