import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import useCurrentUser from '../hooks/useCurrentUser';
import familyLogo from "../styles/images/Family.jpg";

import Button from "./Button";
import { useFetchGroups } from '../hooks/useFetchGroups.jsx';

const GroupsList = () => {
  const { user, error: userError } = useCurrentUser();
  const userID = user?.id;
  const { groups, error: groupError, loading } = useFetchGroups(userID ?? -1);

  let navigate = useNavigate();

  if (userError) return <div>{userError}</div>;
  if (groupError) return <div>{groupError}</div>;
  if (loading) return <p>Loading groups...</p>;

  function handleClick(group) {
    navigate(`/groups/${userID}/${group.id}`);
  }

  return (
    <div className="tiles-container">
      {groups.length === 0 ? (
        <div>
          <Button label="Create New Group" onClick={() => navigate("/groups/create")}>
            Create New Group
          </Button>
        </div>
      ) : (
        <div>
          <div>
            <h2>All Groups</h2>
          </div>
          <div className="tiles-container"> 
          <div className="tile-list">
            {groups.map((group) => (
              <div
                key={group.id}
                className="tile"
                onClick={() => handleClick(group)}>
                <h3>{group.name}</h3>
                <img src={familyLogo} className="app-image-list" alt="Family logo" />
                <p className='app-details-list'>No. of Chores: {group.chores?.length ?? 0}<br></br>No. of Events: {group.events?.length ?? 0}</p>
              </div>
            ))}
          </div>
          </div>
          <div>
            <Button label="Create New Group" onClick={() => navigate("/groups/create")}>
              Create New Group
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};

export default GroupsList;