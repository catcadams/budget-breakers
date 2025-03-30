import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { CiEdit } from "react-icons/ci";
import { RiDeleteBin5Line } from "react-icons/ri";
import Button from './Button';
import "../styles/singleChoreStyle.css";
import "../styles/modalStyles.css";
import ModalWindow from "./ModalWindow";
import { useFetchSingleChore } from "../hooks/useFetchChores";
import { isChoreEditableOrDeletable } from "../utils/choreUtils.jsx";

const SingleChorePage = () => {
  const { choreId } = useParams();
  const [userGroupId, setUserGroupId] = useState(1);//for hardcoded user group. Will be replaced later
  const [showModal, setShowModal] = useState(false);
  const { chore, loading, error } = useFetchSingleChore(userGroupId, choreId);

  const navigate = useNavigate();

  if (loading) return <p>Loading chore details...</p>;
  if (error) return <p>Error: {error}</p>;

  const handleDelete = () => {
    setShowModal(false);
    axios.delete(`http://localhost:8080/chores/delete/${choreId}`)
      .then(() => {
        navigate(`/chores/${userGroupId}/list`);
      })
      .catch((error) => {
        console.error("Error deleting chore:", error);
      });
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  return (
    <div className="single-chore">
      <div className="chore-details">
        <div className="chore-name">
          <p><strong>Chore Name:</strong></p>
          <p>{chore.name}</p>
        </div>
        <div className="chore-description">
          <p><strong>Chore Description:</strong></p>
          <p>{chore.description}</p>
        </div>
        <p className="single-chore-earnings">
          <strong>Earnings: </strong>${chore.amountOfEarnings}
        </p>
      </div>
      <div className="chore-actions">
        <div className="tooltip-container">
          <Button
            className={`action-button ${!isChoreEditableOrDeletable ? 'disabled' : ''}`}
            onClick={() => isChoreEditableOrDeletable && navigate(`/chores/${choreId}/edit`)}
            label={<CiEdit size={24} />}/>
          {!isChoreEditableOrDeletable && <span className="tooltip">Chore is in-progress, edit not allowed</span>}
        </div>
        <Button className="action-button" label="Back to the List" onClick={() => navigate(`/chores/${userGroupId}/list`)}>
        </Button>
        <div className="tooltip-container">
          <Button
            className={`action-button ${!isChoreEditableOrDeletable ? 'disabled' : ''}`}
            onClick={() => isChoreEditableOrDeletable && setShowModal(true)}
            label={<RiDeleteBin5Line size={24} />}/>
          {!isChoreEditableOrDeletable && <span className="tooltip">Chore is in-progress, delete not allowed</span>}
        </div>
      </div>

      {showModal && (
        <ModalWindow
          showState={showModal}
          type="warning"
          message="You are about to delete the chore. Click OK to confirm or close the window to return."
          onClose={handleModalClose}
          onConfirm={handleDelete}
        />
      )}
    </div>
  );
};

export default SingleChorePage;
