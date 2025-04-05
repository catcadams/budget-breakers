import React, { useEffect, useState } from 'react'
import axios from 'axios';
import "../styles/singleChoreStyle.css";
import Button from './Button';
import { useParams,useNavigate } from 'react-router-dom';
import NumericInputField from './NumericInputField';
import { ProgressBar } from 'react-bootstrap';
import ModalWindow from "./ModalWindow";

export default function EventDetails() {
    sessionStorage.setItem("username", "Amy");
    sessionStorage.setItem("accountType", "ADULT");
    const [isVisible, setIsVisible] = useState(sessionStorage.getItem("accountType")== "ADULT");

    const {userGroupId, eventId } = useParams();
    const [newErrors, setErrors] = useState({});
    const navigate = useNavigate(); 
    const [event, setEvent] = useState(null);
    const [formData, setFormData] = useState({amountOfContribution: "",});
    const [message, setMessage] = useState("");
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    
    const failedMessage =
        "Oops! Something went wrong while contributing to the event. Give it another try!";
    const successMessage = "Hooray! Your contribution to the event has been successfully made.";
    
    useEffect(() => {
        const getEvent = () => {axios.get(`http://localhost:8080/events/${userGroupId}/${eventId}`)
          .then(response => {
            setEvent(response.data);
            setErrors({});
          })
          .catch(error => {
            setErrors('Failed to load event details');
            console.error('Error fetching event details:', error);
          });
        };
        getEvent();
      }, [userGroupId, eventId]);

      if (event === null) {
        return <p>Loading event details...</p>;
      }

      const validateForm = () => {
        let isValid = true;
        let newErrors = {};
        
        if (
          !formData.amountOfContribution ||
          isNaN(formData.amountOfContribution) ||
          formData.amountOfContribution < 0
        ) {
          newErrors.amountOfContribution = "Contribution is required and must be a postive number";
          isValid = false;
        }
        setErrors(newErrors);
        return isValid;
      }

      function addContribution(event){
        event.preventDefault();
        if(!validateForm()) return;
        const url = `http://localhost:8080/events/contribute/${userGroupId}/${eventId}`; 
        fetch(url, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData),
        })
          .then((response) => {
            if (response.ok) {
              setMessage(successMessage);
              setModalType("success");
              setErrors({});
            } else {
              setMessage(failedMessage);
              setModalType("danger");
            }
            setShowModal(true);
          })
          .catch((error) => {
            setMessage(failedMessage);
            setModalType("danger");
            setShowModal(true);
          });

          setFormData({amountOfContribution: "",});
      }
  return (
    <div className="tiles-container">
      <div className='title'><h3> View Event</h3></div>
      <div className="contribute-container">
        <div class="progressBar">
          <ProgressBar animated now={event.eventEarnings} max={event.eventBudget} />        
        </div>
        <form>
          <NumericInputField label="Amount to Contribute" name="amountOfContribution" value ={formData.amountOfContribution} setFormData={setFormData} />
          {newErrors.amountOfContribution && (
          <p className="error">{newErrors.amountOfContribution}</p>
        )}
          <Button label="Contribute" onClick={addContribution}></Button>
         </form>
      </div>
      <div className="event-form-container">
        <p>Event Name: {event.eventName}</p>
        <p>Event Description: {event.eventDescription}</p>
        <p>Fund Available: {event.eventEarnings}</p>
        <p>Budget: {event.eventBudget}</p>
        <p>Location: {event.eventLocation}</p>
        <p>Event Date: {event.eventDate}</p>
        <Button label="Back to Event List" onClick={() => navigate(`/events/${userGroupId}/list`)}></Button>
          <div style={{ display: isVisible ? 'block' : 'none' }}>
              <Button label="Update" onClick={() => navigate(`/events/edit/${userGroupId}/${eventId}`)}></Button>
          </div>
      </div>
      </div>
  )
}
