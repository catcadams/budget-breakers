import React, { useEffect, useState } from "react";
import WeatherTile from "./WeatherTile";
import useCurrentUser from "../hooks/useCurrentUser";
import "../styles/mainPageStyle.css";

const MainPage = () => {
    const { user, error } = useCurrentUser();
    const [nextHoliday, setNextHoliday] = useState(null);

    useEffect(() => {
        fetch("https://date.nager.at/api/v3/NextPublicHolidays/US")//for MVP we have only US as lcoation.
            .then((res) => res.json())
            .then((data) => {
                if (Array.isArray(data) && data.length > 0) {
                    setNextHoliday(data[0]);
                }
            })
            .catch((err) => console.error("Failed to fetch holiday info:", err));
    }, []);

    return (
        <div className="main-container">
            <div className="welcome-box">
                <h1>Welcome, {user?.firstName}!</h1>
                <p>Glad to see you! Your group adventures are just getting started.</p>
                {nextHoliday && (
                    <p>
                        Did you know that on <strong><strong>{nextHoliday.date}</strong></strong> we’ve 
                        got <strong>{nextHoliday.localName}</strong>?
                        Sounds like the perfect excuse for some fun activities. Let’s plan something awesome!
                    </p>
                )}
            </div>
            <WeatherTile />
        </div>
    );
};

export default MainPage;
