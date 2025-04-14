import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useCurrentUser from '../hooks/useCurrentUser';
import { useFetchGroupNumber } from '../hooks/useFetchChores.jsx';
import { useFetchGroups } from '../hooks/useFetchGroups.jsx';
import "../styles/choreCreationFormStyle.css";
import Button from "./Button";
import DropdownField from "./DropdownField";
import ModalWindow from "./ModalWindow";
import NumericInputField from "./NumericInputField";
import TextAreaInputField from "./TextAreaInputField";
import TextInputField from "./TextInputField";
import { isAdult } from "../utils/userUtils.jsx";


const ChoreCreationForm = () => {
    const { user } = useCurrentUser();
    const userID = user?.id;
    const [formData, setFormData] = useState({ name: "", description: "", amountOfEarnings: "", userGroupName: "" });
    const [message, setMessage] = useState("");
    const [errors, setErrors] = useState({});
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const [showAdultWarningModal, setShowAdultWarningModal] = useState(false);
    const navigate = useNavigate();
    const { groups, loading, error: groupError } = useFetchGroups(userID ?? -1);
    console.log("fetched groups size is : " + groups.length);
    const { warningMessage, modalType: warningModalType, showModal: showWarningModal } = useFetchGroupNumber(groups, loading, user);

    const failedMessage = "Oops! Something went wrong while creating the chore. Looks like the universe is not ready for this one. Give it another try!";
    const successMessage = "Hooray! Your chore has been successfully created. Now get ready to watch the magic of hard work unfold!";

    useEffect(() => {
        if (user && !isAdult(user)) {
            setShowAdultWarningModal(true);
        }
    }, [user]);

    let validateUserInputs = () => {
        let errors = {};
        if (!formData.name.trim() || formData.name.length < 3 || formData.name.length > 50) errors.name = "Name is required and must be between 3 and 50 characters.";
        if (!formData.amountOfEarnings || isNaN(formData.amountOfEarnings) || formData.amountOfEarnings < 0) {
            errors.amountOfEarnings = "Amount is required and must be a positive number";
        }
        if (!formData.userGroupName) {
            errors.userGroupName = "Group is required.";
        }
        setErrors(errors);
        return Object.keys(errors).length === 0;
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validateUserInputs()) return;
        fetch("http://localhost:8080/chores/create", {
            method: "POST",
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

    }

    const handleModalClose = () => {
        setShowModal(false);
        if (modalType === "success") {
            const selectedGroup = groups.find(group => group.name === formData.userGroupName);
            if (selectedGroup) {
                navigate(`/groups/${user.id}/${selectedGroup.id}`);
            } else {
                navigate(`/groups`);
            }
        }
    };

    const handleAdultModalClose = () => {
        setShowAdultWarningModal(false);
        navigate("/Home");
    };

    const handleWarningClose = () => {
        setShowModal(false);
        navigate(`/groups`);
    };

    return (
        <>
            <form className="chore-form-container">
                <p>Create Chores, Fund the Fun!</p>
                <div>Got a family event you're excited about? Time to get things done! Create chores now and let your kids
                    complete them. Watch them contributing towards the next big family adventure! The cleaner the room,
                    the closer the goal!
                </div>
                <div>
                    <TextInputField label="Chore Name" name="name" value={formData.name} setFormData={setFormData} />
                    {errors.name && <p className="error">{errors.name}</p>}
                    <TextAreaInputField label="Chore Description" name="description" value={formData.description} setFormData={setFormData} />
                    <NumericInputField label="Earning Amount, $" name="amountOfEarnings" value={formData.amountOfEarnings} setFormData={setFormData} />
                    {errors.amountOfEarnings && <p className="error">{errors.amountOfEarnings}</p>}
                    <DropdownField label="Group: " options={groups.map(group => group.name)} name="userGroupName" placeholder="Select your group" setFormData={setFormData} />
                    {errors.userGroupName && <p className="error">{errors.userGroupName}</p>}
                    <Button className="chore-btn" label="Create Chore" onClick={handleSubmit} disabled={showModal} />
                </div>
            </form>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleModalClose()} onConfirm={handleModalClose} />
            <ModalWindow
                showState={showWarningModal}
                message={warningMessage}
                type={warningModalType}
                onClose={handleWarningClose}
                onConfirm={handleWarningClose}
            />
            <ModalWindow
                showState={showAdultWarningModal}
                message="Only adult users can create chores."
                type="warning"
                onClose={handleAdultModalClose}
                onConfirm={handleAdultModalClose}
            />
        </>

    );
};

export default ChoreCreationForm;


