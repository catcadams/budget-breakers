import React, { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { isEventBudgetReached } from "../utils/choreUtils";
import "../styles/congratsPageStyle.css"; 
import congrats from "../styles/images/congrats.png"; 


const ChoreCompletionPage = () => {
  const { choreId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const groupID = location.state?.groupID;

  const [events, setEvents] = useState([]);
  const [selectedEventId, setSelectedEventId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);


  useEffect(() => {
    if (!groupID) {
      setError("Group ID not provided.");
      setLoading(false);
      return;
    }
  
    axios.get(`http://localhost:8080/events/${groupID}/list`, { withCredentials: true })
      .then(response => {
        const filteredEvents = response.data.filter(isEventBudgetReached);
        setEvents(filteredEvents);
      })
      .catch(() => setError("Failed to load events"))
      .finally(() => setLoading(false));
  }, [groupID]);

  const handleSubmit = () => {
    if (!selectedEventId || !groupID) return;

    axios.put(
      `http://localhost:8080/chores/${choreId}/complete`,
      {
        eventId: selectedEventId,
        groupId: groupID
      },
      { withCredentials: true }
    )
    .then(() => {
      setShowModal(true);
    })
    .catch(err => console.error("Error assigning to event:", err));
  };

  const handleModalClose = () => {
    setShowModal(false);
    navigate(`/chores/${choreId}`, { state: { groupID } });
  };

  const handleBackToChore = () => {
    navigate(`/chores/${choreId}`, { state: { groupID } });
  };

  const handleCreateEvent = () => {
    navigate(`/event/create`);
  };

  const hasEvents = events.length > 0;

  if (loading) return <p>Loading events...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="congrats-container">
      <div className="overlay">
        {hasEvents ? (
          <>
            <img src={congrats} alt="Congrats" className="congrats-header-img" />
            <p>You’ve completed your chore. Time to make it count!</p>
            <div className="event-selection">
              <h2>Select an event to contribute to:</h2>
              {events.map(event => (
                <label key={event.id} className="event-radio">
                  <input
                    type="radio"
                    name="event"
                    value={event.id}
                    onChange={() => setSelectedEventId(event.id)}
                  />
                   <span class="checkmark"></span>
                  {event.name}
                </label>
              ))}
            </div>
            <Button 
              label="Confirm Selection" 
              onClick={handleSubmit} 
              disabled={!selectedEventId}
            />
          </>
        ) : (
          <>
            <h2>Oops...Looks like your group doesn't have any planned events!</h2>
            <p>You need to create at least one new event first.</p>
            <div className="event-actions">
              <Button label="Create New Event" onClick={handleCreateEvent} />
              <Button label="Back to Chore Details" onClick={handleBackToChore} />
            </div>
          </>
        )}
      </div>

      <ModalWindow
        showState={showModal}
        type="success"
        message="Chore successfully completed and will be contributed to the event once confirmed!"
        onClose={handleModalClose}
        onConfirm={handleModalClose}
      />
    </div>
  );
};


export default ChoreCompletionPage;
