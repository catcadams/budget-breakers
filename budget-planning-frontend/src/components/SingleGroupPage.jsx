import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useFetchSingleGroup } from "../hooks/useFetchGroups";
import Button from "./Button";
import useCurrentUser from '../hooks/useCurrentUser';
import ChoresList from "./ChoresList";

const SingleGroupPage = () => {
  const { groupID } = useParams();
  const { choreID } = useParams();
  const { eventID } = useParams();
  const { user, error: userError } = useCurrentUser();
  const userID = user?.id;
  const { group, error: groupError, loading } = useFetchSingleGroup(userID, groupID);

  const navigate = useNavigate();

  if (loading) return <p>Loading group details...</p>;
  if (userError) return <p>Error: {error}</p>;
  if (groupError) return <p>Error: {error}</p>;


  //  //Clicking on a chore should link to individual chore page
  //  //Need handle click function for chores to link to single chore page
  // Display events related to group
  //  //Clicking on an event should link to individual event page
  //  //Need handle click function for events to link to singe event page
  // Have add member button that has a pop up window for adding members
  //  //Need handle click for add member button
  //  //Need handle submit for adding members
  // Top of page should display group name

  const handleChoreClick = () => {
    navigate(`/chores/${groupID}/${choreID}`);
  };

  const handleEventClick = () => {
    navigate(`/events/${groupID}/${eventID}`);
  }


  return (
    <div>
      <h2>{group.name}</h2>
      <p>{group.description}</p>
      <div>
        {/* This button is a work in progress */}
        <Button label="Add New Member"></Button>
      </div>
      <div className="tiles-container">
        <h3>All Chores</h3>
        <ChoresList />
      </div>
      <div className="tiles-container">
        <h3>All Events</h3>
        {group.events.length === 0 ? (
          <p>No events available for your group.</p>
        ) : (
          <div className="tile-list">
            {group.events.map((event) => (
              <div
                key={event.id}
                className="tile"
                onClick={() => handleEventClick(event)}
              >
                <h3 className="chore-title">{event.name}</h3>
                <p className="chore-earnings">${event.eventBudget}</p>
              </div>
            ))}
          </div>
        )}
      </div>
      <div className="tiles-container">
        <h3>Group Members</h3>
          <div>
            {group.users.map((user) => (
              <div key={user.id} >
                <p>{user.firstName} {user.lastName}</p>
              </div>
            ))}
          </div>
      </div>
    </div>
  );
};

export default SingleGroupPage;
