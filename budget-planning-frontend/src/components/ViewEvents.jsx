import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import Button from "./Button";
import { useFetchEvents } from "../hooks/useFetchEvents";

export default function ViewEvents() {
  const { groupID } = useParams();
  const { events, error, loading: eventLoading } = useFetchEvents(groupID);
  let navigate = useNavigate();

  if (error) return <div>{error}</div>;
  if (eventLoading) return <p>Loading Events...</p>;

  function handleClick(event) {
    console.log(`Event clicked: ${event.eventName}`);
    navigate(`/events/${groupID}/${event.eventId}`);
  }
  return (
    <div className="tiles-container">
      <div className="tile-list">
        {events.map((event, index) => (
          <div
            key={event.id}
            className="tile"
            onClick={() => handleClick(event)}
          >
            <h3>{event.eventName}</h3>
            <p>Fund Available: {event.eventEarnings}$</p>
            <p>Budget: {event.eventBudget}$</p>
            <p>
              <progress value={event.eventEarnings} max={event.eventBudget} />
            </p>
          </div>
        ))}
      </div>
      <div>
        <Button
          label="Create New Event"
          onClick={() => navigate("/event/create")}
        >
          Create New Event
        </Button>
      </div>
    </div>
  );
}
