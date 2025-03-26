import React, { useEffect, useState } from 'react';
import "../styles/choreListStyle.css";
import image from "../styles/images/foldedShirts.png";
import { useNavigate } from "react-router-dom";

import axios from 'axios';

const ChoresList = () => {
  const [chores, setChores] = useState([]);
  const [error, setError] = useState(null);
  const [userGroupId, setUserGroupId] = useState(1); 
  //hardcoded userGroupId here, should be replaced later when groups will be implemented

  let navigate = useNavigate(); 
  useEffect(() => {
    const fetchChores = () => {
      axios
        .get(`http://localhost:8080/chores/${userGroupId}/list`)
        .then((response) => {
          setChores(response.data);  
          setError(null);  
        })
        .catch((err) => {
          setError('Failed to load chores');
          console.error(err);
        });
    };

    fetchChores();
  }, [userGroupId]);

  if (error) return <div>{error}</div>;

  function handleClick(chore) {
    console.log(`Chore clicked: ${chore.name}`);
    navigate(`/chores/${userGroupId}/${chore.id}`);
  }


  return (
    <div className="chores-container">
    <div className="chore-list">
      {chores.map((chore, index) => (
        <div
          key={chore.id}
          className="chore-tile"
          onClick={() => handleClick(chore)}>
          <h3>{chore.name}</h3>
          <img src={image} alt="Chore" className="chore-image" />
          <p className="chore-earnings">${chore.amountOfEarnings}</p>
          
        </div>
      ))}
    </div>
    </div>
  );

};



export default ChoresList;

