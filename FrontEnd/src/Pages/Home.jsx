import React from 'react';
import '../Styles/Home.css';
import team1 from '../assets/team1.jpeg';
import team2 from '../assets/team2.jpeg';

const Home = () => {
  return (
    <div className="career-home">
      <div className="career-left">
        <h1>Build Your Career With Us.</h1>
        <h2>Let's Shake Hands & Explore Opportunities Together.</h2>
        <button className="explore-btn">Explore Opportunities</button>
      </div>
      <div className="career-right">
        <div className="image-box blue-bg">
          <img src={team1} alt="Team 1" />
        </div>
        <div className="image-box green-bg">
          <img src={team2} alt="Team 2" />
        </div>
      </div>
    </div>
  );
};

export default Home;
