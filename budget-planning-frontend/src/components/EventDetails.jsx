import React, { useEffect, useState } from 'react'
import axios from 'axios';
import "../styles/singleChoreStyle.css";
import Button from './Button';
import { useParams,useNavigate } from 'react-router-dom';
import TextInputField from './TextInputField';
import TextAreaInputField from './TextAreaInputField';
import NumericInputField from './NumericInputField';
import DateInputField from './DateInputField';
import { ProgressBar } from 'react-bootstrap';

export default function EventDetails() {
    sessionStorage.setItem("username", "Amy");
    sessionStorage.setItem("accountType", "ADULT");
    const today = new Date().toISOString().split("T")[0];
    const [isVisible, setIsVisible] = useState(sessionStorage.getItem("accountType")== "ADULT");

    const [data, setData] = useState({
        eventName: "",
        eventBudget: "",
        eventLocation: "",
        eventDescription: "",
        eventDate: "",
      });

    const {userGroupId, eventId } = useParams();
    const [newErrors, setErrors] = useState(null);
    const navigate = useNavigate(); 
    const [event, setEvent] = useState(null);
    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setData((prev) => ({ ...prev, [name]: value }));
    };

    useEffect(() => {
        const getEvent = () => {axios.get(`http://localhost:8080/events/${userGroupId}/${eventId}`)
          .then(response => {
            setEvent(response.data);
            setData(response.data);
            setErrors(null);
          })
          .catch(error => {
            setErrors('Failed to load event details');
            console.error('Error fetching event details:', error);
          });
        };
        getEvent();
      }, [userGroupId, eventId]);

      if (newErrors) return <div>{newErrors}</div>;

      if (event === null) {
        return <p>Loading event details...</p>;
      }

      const editEvent = (event) => {
        alert("inside editEvent");
        axios.post(`http://localhost:8080/events/${userGroupId}/${eventId}/edit`, data).then(response => {
            console.log(response.data);
          })
          .catch(error => {
            console.error(error);
          });
          alert("Event Modified Successfully");
        };
  return (
    <div className='pageBody'>
        <h3> View/Edit Event</h3>
        <div class="progressBar">
        <ProgressBar animated now={event.earnings} max={event.budget} />  
      </div>
    <form>
      <TextInputField label="Event name" name="eventName" value={event.name} setData={setData}/>
      <TextAreaInputField label ="Description" name="eventDescription" value={event.description} setData={setData}/>
      <NumericInputField label="Fund Available" name="eventEarnings" value={event.earnings} setData={setData}/>
      <NumericInputField label="Budget" name="eventBudget" value={event.budget} setData={setData}/>
      <TextInputField label="Location" name="eventLocation" value={event.location} setData={setData} />
      <label for="eventDate">Date: </label>
        <input
          type="date"
          min={today}
          name="eventDate"
          onChange={handleChange}
          value={Date(event.date)}
          setData={setData}/>
      <p><strong>Date: </strong>{event.date}</p>
      <Button label="Back to Event List" onClick={() => navigate(`/events/${userGroupId}/list`)}></Button>
        <div style={{ display: isVisible ? 'block' : 'none' }}>
            <Button label="Update Event" onClick={() => editEvent}></Button>
        </div>
      </form>
      </div>
  )
}
