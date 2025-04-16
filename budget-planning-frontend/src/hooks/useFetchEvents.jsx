import { useState, useEffect } from "react";
import axios from "axios";

export const useFetchEvents = (groupID) => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setErrors] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/events/${groupID}/list`, {
        withCredentials: true,
      })
      .then((response) => {
        setEvents(response.data);
        setErrors(null);
      })
      .catch((err) => {
        setErrors("Failed to load events");
        console.error(err);
      })
      .finally(() => setLoading(false));
  }, [groupID]);

  return { events, loading, error };
};

export const useFetchEventDetails = (userGroupId, eventId) => {
  const [event, setEvent] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setErrors] = useState(null);
  const [isBudget, setIsBudget] = useState(false);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/events/${userGroupId}/${eventId}`, {
        withCredentials: true,
      })
      .then((response) => {
        setEvent(response.data);
        setIsBudget(response.data.budgetAchieved);
        setErrors(null);
      })
      .catch((error) => {
        setErrors("Failed to load event details");
        console.error("Error fetching event details:", error);
      })
      .finally(() => setLoading(false));
  }, [userGroupId, eventId]);

  return { event, loading, error, isBudget };
};