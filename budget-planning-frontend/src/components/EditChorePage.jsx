import { useState, useEffect } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import TextInputField from "./TextInputField";
import NumericInputField from "./NumericInputField";
import TextAreaInputField from "./TextAreaInputField";
import DropdownField from "./DropdownField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import "../styles/choreCreationFormStyle.css";
import "../styles/singleChoreStyle.css";
import { GrClose } from "react-icons/gr";
import { useFetchSingleChore } from "../hooks/useFetchChores";

const EditChorePage = () => {
    const location = useLocation();
    const groupID = location.state?.groupID;
    const choreId = location.state?.choreId;
    console.log("Group ID on edit:", groupID);
    console.log("Chore ID on edit:", choreId);
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        amountOfEarnings: "",
    });
    const [message, setMessage] = useState("");
    const [errors, setErrors] = useState({});
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const navigate = useNavigate();
    const { chore, loading, error } = useFetchSingleChore(groupID, choreId);

    useEffect(() => {
        if (chore) {
            setFormData({
                name: chore.name,
                description: chore.description,
                amountOfEarnings: chore.amountOfEarnings,
            });
        }
    }, [chore]);

    const failedMessage = "Oops! Something went wrong while updating the chore.Please try again.";
    const successMessage = "Chore successfully updated!";

    const validateUserInputs = () => {
        let errors = {};
        if (!formData.name.trim() || formData.name.length < 3 || formData.name.length > 50) {
            errors.name = "Name is required and must be between 3 and 50 characters.";
        }
        if (!formData.amountOfEarnings || isNaN(formData.amountOfEarnings) || formData.amountOfEarnings < 0) {
            errors.amountOfEarnings = "Amount is required and must be a positive number";
        }
        setErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validateUserInputs()) return;

        fetch(`http://localhost:8080/chores/${choreId}/edit`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
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
    };

    const handleModalClose = () => {
        setShowModal(false);
        if (modalType === "success") {
            navigate(`/chores/${groupID}/${choreId}`, { state: { groupID, choreId } });
        }
    };

    return (
        <>
            <form className="chore-form-container">
                <div className="close-btn-container">
                    <Button label={<GrClose size={24} />} onClick={() => navigate(`/chores/${groupID}/${choreId}`, { state: { groupID, choreId } })} className="close-btn" />
                </div>
                <p>Update the Details of the Chore</p>
                <div>Ready to update your family chores? Edit the details of the chore, adjust earnings, and keep your home running smoothly!</div>
                <div>
                    <TextInputField label="Chore Name" name="name" value={formData.name} setFormData={setFormData} />
                    {errors.name && <p className="error">{errors.name}</p>}
                    <TextAreaInputField label="Chore Description" name="description" value={formData.description} setFormData={setFormData} />
                    <NumericInputField label="Earning Amount, $" name="amountOfEarnings" value={formData.amountOfEarnings} setFormData={setFormData} />
                    {errors.amountOfEarnings && <p className="error">{errors.amountOfEarnings}</p>}
                    <Button className="" label="Update Chore" onClick={handleSubmit} />
                </div>
            </form>
            <ModalWindow showState={showModal} message={message} type={modalType} onConfirm={handleModalClose} onClose={handleModalClose} />
        </>
    );
};

export default EditChorePage;
