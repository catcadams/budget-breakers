import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import "../styles/singleChoreStyle.css";
import Button from './Button';

const SingleChorePage = () => {
    const {userGroupId, choreId } = useParams();
    const navigate = useNavigate(); 

  const [chore, setChore] = useState(null);

//   const userGroupId = 1;//hardcoded for now for testing purposes
//   const choreId = 1;//hardcoded for now for testing purposes

  useEffect(() => {
    axios.get(`http://localhost:8080/chores/${userGroupId}/${choreId}`)
      .then(response => {
        setChore(response.data);
      })
      .catch(error => {
        console.error('Error fetching chore details:', error);
      });
  }, [userGroupId, choreId]); 

    // without this code it is failing as chore returns as null. 
    if (chore === null) {
        return <p>Loading chore details...</p>;
      }

  return (
    <div>
    <div className="single-chore-container">
      <h1 className="single-chore-header">{chore.name}</h1>
      <p>{chore.description}</p>
      <p className='single-chore-earnings' ><strong>Earnings: </strong>${chore.amountOfEarnings}</p>
    </div>
      <Button className="back-button" label="Back to the Chores List" onClick={() => navigate(`/chores/${userGroupId}/list`)}></Button>
      </div>
  );
};

export default SingleChorePage;
