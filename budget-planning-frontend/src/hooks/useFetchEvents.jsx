import { useState, useEffect } from "react";
import axios from "axios";

export const useFetchEvents = (groupID) => {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setErrors] = useState(null);

    useEffect(() => {
          axios
            .get(`http://localhost:8080/events/${groupID}/list`, { withCredentials: true })
            .then((response) => {
              setEvents(response.data);  
              setErrors(null);  
            })
            .catch((err) => {
              setErrors('Failed to load events');
              console.error(err);
            })
            .finally(() => setLoading(false));

      }, [groupID]);

      return { events, loading, error };
};