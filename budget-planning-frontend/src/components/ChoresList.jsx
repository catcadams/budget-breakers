import React, { useEffect, useState } from 'react';
import "../styles/choreListStyle.css";
import { useNavigate, useParams } from "react-router-dom";
import { getChoreImage } from "../utils/choreUtils.jsx";
import { isAdult } from "../utils/userUtils.jsx";

import Button from "./Button";
import { useFetchChores } from "../hooks/useFetchChores";
import useCurrentUser from '../hooks/useCurrentUser';

const ChoresList = () => {
  const { groupID } = useParams();
  const { user, error: userError } = useCurrentUser();
  const { chores, error, loading: choreLoading } = useFetchChores(groupID);

  let navigate = useNavigate();

  if (!user) return <p>Loading user...</p>;
  if (error) return <div>{error}</div>;
  if (choreLoading) return <p>Loading chores...</p>;
  if (userError) return <div>{userError}</div>;

  function handleClick(chore) {
    navigate(`/chores/${groupID}/${chore.id}`, { state: { groupID } });
  }

  return (
    <div>
      {chores.length === 0 ? (
        <div>
          <p>
            {isAdult(user)
              ? "No chores available for this group. Click below to add one!"
              : "Looks like this group is chore-free… until an adult adds chores. Stay tuned!"}
          </p>
          {isAdult(user) && (
            <Button label="Create Chore" onClick={() => navigate("/chores/create")}>
              Create Chore
            </Button>
          )}
        </div>
      ) : (
        <>
          <div>
            <p>
              {isAdult(user)
                ? "Have ideas for new chores? Click below to add them!"
                : "Great time to be a family hero! Complete the chore and make your contribution towards planned family events!"}
            </p>
            {isAdult(user) && (
              <Button label="Create Chore" onClick={() => navigate("/chores/create")}>
                Create Chore
              </Button>
            )}
          </div>
          <div className="tiles-container">
          <div className="tile-list">
            {chores.map((chore) => (
              <div
                key={chore.id}
                className="tile"
                onClick={() => handleClick(chore)}>
                <h3 className="chore-title">{chore.name}</h3>
                <img src={getChoreImage(chore.status)} alt="Chore" className="chore-image-list" />
                <p className="chore-earnings">${chore.amountOfEarnings}</p>
              </div>
            ))}
          </div>
          </div>
        </>
      )}

    </div>

  );
};

export default ChoresList;