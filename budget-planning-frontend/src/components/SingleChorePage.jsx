import React, { useState } from "react";
import axios from "axios";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { CiEdit } from "react-icons/ci";
import { RiDeleteBin5Line } from "react-icons/ri";
import Button from './Button';
import "../styles/singleChoreStyle.css";
import ModalWindow from "./ModalWindow";
import { useFetchSingleChore } from "../hooks/useFetchChores";
import { isChoreEditableOrDeletable, getChoreImage, getChoreStatusMessage } from "../utils/choreUtils.jsx";
import useCurrentUser from '../hooks/useCurrentUser';
import { isAdult, isCurrentUserEqualsAssignedUser } from "../utils/userUtils.jsx";

const SingleChorePage = () => {
  const location = useLocation();
  const groupID = location.state?.groupID;
  const { choreId } = useParams();
  console.log("Group ID:", groupID);
  console.log("Chore ID:", choreId);
  const { user, error: userError } = useCurrentUser();
  const [showModal, setShowModal] = useState(false);
  const { chore, loading, error: choreError, status } = useFetchSingleChore(groupID, choreId);

  const navigate = useNavigate();

  if (loading) return <p>Loading chore details...</p>;
  if (userError) return <p>Error: {userError}</p>;
  if (choreError) return <p>Error: {choreError}</p>;

  const handleDelete = () => {
    setShowModal(false);
    axios.delete(`http://localhost:8080/chores/delete/${choreId}`, { withCredentials: true })
      .then(() => {
        navigate(`/groups/${user.id}/${groupID}`);
      })
      .catch((error) => {
        console.error("Error deleting chore:", error);
      });
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  const handleAssignToMe = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/assign`, { groupID }, { withCredentials: true })
      .then((response) => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error assigning chore:", error);
      });
  };

  const handleUnassign = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/unassign`, { groupID }, { withCredentials: true })
      .then((response) => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error removing the assignment:", error);
      });
  };

  const handleMarkAsCompleted = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/complete`, { withCredentials: true })
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error marking chore as completed:", error);
      });
  };

  const handleConfirmContribution = () => {
    axios.put(`http://localhost:8080/chores/${choreId}/contribute`, { withCredentials: true })
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error marking chore as confirmed:", error);
      });
  };

  return (
    <div className="single-chore">
      <div className="chore-details-grid">
        <div className="chore-image-container">
          <p style={{ color: "chocolate" }}>{getChoreStatusMessage(chore)}</p>
          <img src={getChoreImage(chore.status)} alt="Chore" className="chore-image" />
          <div>
            {chore.status === "OPEN" && !isAdult(user) && (
              <Button onClick={handleAssignToMe} label="Assign to Me" />
            )}
            {chore.status === "IN_PROGRESS" && isCurrentUserEqualsAssignedUser(chore, user) && (
              <>
                <Button onClick={handleUnassign} label="Unassign" />
                <Button onClick={handleMarkAsCompleted} label="Mark as Completed" />
              </>
            )}
            {chore.status === "PENDING" && isAdult(user)(
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
            {isAdult(user) && chore.status !== "COMPLETE" && (
              <>
                <div className="tooltip-container">
                  <Button
                    className={`action-button${!isChoreEditableOrDeletable(chore.status) ? '-disabled' : ''}`}
                    onClick={() => isChoreEditableOrDeletable(chore.status) && navigate(`/chores/${choreId}/edit`, { state: { choreId, groupID }})}
                    label={<CiEdit size={24} />}
                  />
                  {!isChoreEditableOrDeletable(chore.status) && <span className="tooltip">Chore is in-progress, edit not allowed</span>}
                </div>
              </>
            )}
            <Button
              className="action-button"
              label="Back to the List"
              onClick={() => navigate(`/groups/${user.id}/${groupID}`)}
            />
            {isAdult(user) && chore.status !== "COMPLETE" && (
              <>
                <div className="tooltip-container">
                  <Button
                    className={`action-button${!isChoreEditableOrDeletable(chore.status) ? '-disabled' : ''}`}
                    onClick={() => isChoreEditableOrDeletable(chore.status) && setShowModal(true)}
                    label={<RiDeleteBin5Line size={24} />}
                  />
                  {!isChoreEditableOrDeletable(chore.status) && <span className="tooltip">Chore is in-progress, delete not allowed</span>}
                </div>
              </>
            )}
          </div>
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
    </div >
  );
};

export default SingleChorePage;
