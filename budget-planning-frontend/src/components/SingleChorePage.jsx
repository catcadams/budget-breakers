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
import { getChoreImage } from "../utils/choreUtils.jsx";
import { getChoreStatusMessage } from "../utils/choreUtils.jsx";

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

  const handleAssignToMe = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/assign`, { userGroupId })
      .then((response) => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error assigning chore:", error);
      });
  };

  const handleMarkAsCompleted = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/complete`)
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error marking chore as completed:", error);
      });
  };

  const handleConfirmContribution = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/contribute`)
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error marking chore as completed:", error);
      });
  };

  return (
    <div className="single-chore">
      <div className="chore-details-grid">
        <div className="chore-image-container">
          <p style={{ color: "chocolate" }}>{getChoreStatusMessage(chore)}</p>
          <img src={getChoreImage(chore.status)} alt="Chore" className="chore-image" />
          <div>
            {chore.status === "OPEN" && (
              <Button onClick={handleAssignToMe} label="Assign to Me" />
            )}
            {chore.status === "IN_PROGRESS" && (
              <Button onClick={handleMarkAsCompleted} label="Mark as Completed" />
            )}
            {chore.status === "PENDING" && (
              <Button onClick={handleConfirmContribution} label="Confirm to Contribute" />
            )}
          </div>
        </div>
        <div className="chore-info-container">
          <div>
            <p><strong>Chore Name:</strong></p>
            <p>{chore.name}</p>
          </div>
          <div>
            <p><strong>Chore Description:</strong></p>
            <p>{chore.description}</p>
          </div>
          <p className="single-chore-earnings">
            <strong>Earnings: </strong>${chore.amountOfEarnings}
          </p>
          <div className="chore-actions">
            <div className="tooltip-container">
              <Button
                className={`action-button${!isChoreEditableOrDeletable(chore.status) ? '-disabled' : ''}`}
                onClick={() => isChoreEditableOrDeletable(chore.status) && navigate(`/chores/${choreId}/edit`)}
                label={<CiEdit size={24} />}
              />
              {!isChoreEditableOrDeletable(chore.status) && <span className="tooltip">Chore is in-progress, edit not allowed</span>}
            </div>
            <Button
              className="action-button"
              label="Back to the List"
              onClick={() => navigate(`/chores/${userGroupId}/list`)}
            />
            <div className="tooltip-container">
              <Button
                className={`action-button${!isChoreEditableOrDeletable(chore.status) ? '-disabled' : ''}`}
                onClick={() => isChoreEditableOrDeletable(chore.status) && setShowModal(true)}
                label={<RiDeleteBin5Line size={24} />}
              />
              {!isChoreEditableOrDeletable(chore.status) && <span className="tooltip">Chore is in-progress, delete not allowed</span>}
            </div>
          </div>
        </div>
      </div>

      {
        showModal && (
          <ModalWindow
            showState={showModal}
            type="warning"
            message="You are about to delete the chore. Click OK to confirm or close the window to return."
            onClose={handleModalClose}
            onConfirm={handleDelete}
          />
        )
      }
    </div >
  );
};

export default SingleChorePage;
