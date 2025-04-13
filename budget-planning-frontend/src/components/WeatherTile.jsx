import React, { useState, useEffect } from "react";
import image from "../styles/images/weather.gif";

const WeatherTile = () => {
  const [location, setLocation] = useState({ latitude: null, longitude: null });
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setLocation({ latitude, longitude });
          fetchWeather(latitude, longitude);
        },
        (error) => {
          setError("Geolocation permission issues.");
        }
      );
    } else {
      setError("Geolocation is not supported.");
    }
  }, []);

//official documentation for this APi is here:
// https://open-meteo.com/en/docs?temperature_unit=fahrenheit&current=temperature_2m,is_day,rain,wind_speed_10m,wind_direction_10m&wind_speed_unit=mph&precipitation_unit=inch&forecast_days=3
  const fetchWeather = async (lat, lon) => {
    try {
      const response = await fetch(
        `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current=temperature_2m,is_day,rain,wind_speed_10m,wind_direction_10m&forecast_days=3&wind_speed_unit=mph&temperature_unit=fahrenheit&precipitation_unit=inch`
      );
      const data = await response.json();
      setWeather(data.current);
    } catch (error) {
      setError("Failed to fetch weather data.");
    } 
  };

  const getWindDirection = (degrees) => {
    if (degrees >= 0 && degrees < 22.5) return 'North';
    if (degrees >= 22.5 && degrees < 67.5) return 'North-East';
    if (degrees >= 67.5 && degrees < 112.5) return 'East';
    if (degrees >= 112.5 && degrees < 157.5) return 'South-East';
    if (degrees >= 157.5 && degrees < 202.5) return 'South';
    if (degrees >= 202.5 && degrees < 247.5) return 'South-West';
    if (degrees >= 247.5 && degrees < 292.5) return 'West';
    if (degrees >= 292.5 && degrees < 337.5) return 'North-West';
    return 'North';
  };
  

  return (
    <div className="weather-container">
     {weather && (
        <>
          <div className="weather-tile">
            <h2>
            <img src={image} alt="Weather icon" className="weather-icon" />
            Weather at your location: </h2>
            <p><strong>Temperature:</strong> {weather.temperature_2m}°F</p>
            <p><strong>Rain:</strong> {weather.rain} inches</p>
            <p><strong>Wind:</strong> {weather.wind_speed_10m} mph</p>
            <p><strong>Wind Direction:</strong> {getWindDirection(weather.wind_direction_10m)}</p>
          </div>
        </>
      )}
    </div>
  );
};

export default WeatherTile;
