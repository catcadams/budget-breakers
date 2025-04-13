import React from "react";
import WeatherTile from "./WeatherTile";
import useCurrentUser from "../hooks/useCurrentUser";
import "../styles/mainPageStyle.css"; 

const MainPage = () => {
    const { user, error } = useCurrentUser();

    return (
        <div className="main-container">
          <div className="welcome-box">
            <h1>Welcome, {user?.firstName}!</h1>
            <p>Looks like a great time to plan something fun with your group!</p>
          </div>
          <WeatherTile />
        </div>
      );
    };

export default MainPage;
