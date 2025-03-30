import React, { useEffect, useState } from 'react';
import "../styles/choreListStyle.css";
import { useNavigate } from "react-router-dom"
import { getChoreImage } from "../utils/choreUtils.jsx";

import Button from "./Button";
import { useFetchChores } from "../hooks/useFetchChores";

const ChoresList = () => {
  const [userGroupId, setUserGroupId] = useState(1);
  //hardcoded userGroupId here, should be replaced later when groups will be implemented
  const { chores, error, loading } = useFetchChores(userGroupId);

  let navigate = useNavigate();

  if (error) return <div>{error}</div>;
  if (loading) return <p>Loading chores...</p>;

  function handleClick(chore) {
    navigate(`/chores/${userGroupId}/${chore.id}`);
  }

  return (
    <div className="tiles-container">
      {chores.length === 0 ? (
        <div className="no-chores-message">
          <p>No chores available for this group. Click below to add one!</p>
          <Button label="Create Chore" onClick={() => navigate("/chores/create")}>
            Create Chore
          </Button>
        </div>
      ) : (
        <div className="tile-list">
          {chores.map((chore) => (
            <div
              key={chore.id}
              className="tile"
              onClick={() => handleClick(chore)}>
              <h3 className="chore-title">{chore.name}</h3>
              <img src={getChoreImage(chore.status)} alt="Chore" className="chore-image" />
              <p className="chore-earnings">${chore.amountOfEarnings}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ChoresList;