import React, { useState } from "react";

export default function CreateEvent() {
  const today = new Date().toISOString().split("T")[0];
  const [data, setData] = useState({
    eventName: "",
    eventBudget: "",
    eventLocation: "",
    eventDescription: "",
    eventDate: "",
  });
  const [newErrors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setData((prev) => ({ ...prev, [name]: value }));
};

  const validateForm = () => {
    let isValid = true;
    let newErrors = {};
    if (
      !data.eventName.trim() ||
      data.eventName.length < 3 ||
      data.eventName.length > 50
    ) {
      newErrors.eventName =
        "Name is required";
      isValid = false;
    }
    if (!data.eventBudget || isNaN(data.eventBudget) || data.eventBudget < 0) {
      newErrors.eventBudget =
        "Budget is required";
      isValid = false;
    }
    setErrors(newErrors);
    return isValid;
  };

  const createEvent = (event) => {
    event.preventDefault();
    if (!validateForm()) return;

    fetch("http://localhost:8080/events/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    alert("Event Created Successfully");
  };

  return (
    <div class="pageBody">
      <form class="createEventForm">
        <h3> Create Event</h3>
        <label for="eventName">Event Name: </label>
        <input
          type="text"
          id="eventName"
          name="eventName"
          onChange={handleChange}
          setData={setData}
        ></input>
        <br></br>
        {newErrors.eventName && <p className="error">{newErrors.eventName}</p>}
        <label for="eventBudget">Budget: </label>
        <input
          type="number"
          id="eventBudget"
          name="eventBudget"
          onChange={handleChange}
          setData={setData}
        ></input>
        <br></br>
        {newErrors.eventBudget && (
          <p className="error">{newErrors.eventBudget}</p>
        )}
        <label for="eventLocation">Location: </label>
        <input
          type="text"
          id="eventLocation"
          name="eventLocation"
          onChange={handleChange}
          setDate={setData}
        ></input>
        <br />
        <label for="eventDescription">Description: </label>
        <textarea
          id="eventDescription"
          name="eventDescription"
          onChange={handleChange}
          setData={setData}
        ></textarea>
        <br />
        <label for="eventDate">Date: </label>
        <input
          type="date"
          min={today}
          name="eventDate"
          onChange={handleChange}
          setData={setData}
        />{" "}
        <br />
        <button type="submit" label="Create Event" onClick={createEvent}>
          Create Event
        </button>
      </form>
    </div>
  );
}
