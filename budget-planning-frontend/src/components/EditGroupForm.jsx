import { useEffect, useState } from "react";
import TextInputField from "./TextInputField";
import TextAreaInputField from "./TextAreaInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import useCurrentUser from '../hooks/useCurrentUser';
import { useFetchSingleGroup } from "../hooks/useFetchGroups";
import axios from "axios";

const EditGroupForm = () => {
    const { user, error } = useCurrentUser();
    const {groupID} = useParams();
    const [formData, setFormData] = useState({name: "", description: ""});
    const [errors, setErrors] = useState({})
    const [message, setMessage] = useState("");
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const userID = user?.id;
    console.log(groupID);
    const { group, error: groupError, loading } = useFetchSingleGroup(userID, groupID);
    const navigate = useNavigate();

    useEffect(() => {
        if (group) {
            setFormData({name: group.name, description: group.description});
        }
    }, [group]);

    const failedMessage = "Oops! Something went wrong while creating your group. Please try again.";
    const successMessage = "Hooray! Your group has been successfully created.";

    const validateUserInputs = () => {
        let errors = {};
        let isValid = true;

        if (!formData.name.trim() || formData.name.length < 3 || formData.name.length > 50) {
            errors.name = "Name must be between 3 and 50 characters.";
            isValid = false;
        }
        setErrors(errors); 
        return isValid;
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        if(!validateUserInputs()) return;
        fetch(`http://localhost:8080/groups/${userID}/${groupID}/edit`, {
            method: "PUT", 
            headers: {"Content-Type": "application/json"},
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

    const handleClose = () => {
        setShowModal(false);
        navigate(`/groups/${userID}/${groupID}`, {state: {userID, groupID}});
    }

   
    return (
        <form>
            <h1>Update Group Details</h1>
            <div>
                <TextInputField label="Group Name" name="name" value={formData.name} setFormData={setFormData} />
                {errors.name && <p className="error">{errors.name}</p>}
                <TextAreaInputField label="Group Description" name="description" value={formData.description} setFormData={setFormData} />
                <Button label="Update Group" onClick={handleSubmit} />
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleClose()} />
        </form>
    );
};

export default EditGroupForm;