import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import TextInputField from "./TextInputField";
import NumericInputField from "./NumericInputField";
import TextAreaInputField from "./TextAreaInputField";
import DropdownField from "./DropdownField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import "../styles/choreCreationFormStyle.css";

const EditChorePage = () => {
    const { choreId } = useParams(); 
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        amountOfEarnings: "",
        group: { id: "", name: "" },
    });
    const [message, setMessage] = useState("");
    const [errors, setErrors] = useState({});
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const [dummyGroups, setDummyGroups] = useState([{ id: 1, name: 'Smiths' },{ id: 2, name: 'Adams family' }]); // Fetch  dummy groups, must be replaced later
    const navigate = useNavigate();
    const dummyGroupNames = ['Smiths', 'Adams family'];


    useEffect(() => {
        fetch(`http://localhost:8080/chores/${choreId}`)
            .then((response) => response.json())
            .then((data) => {
                console.log("Group name from response:", data.group?.name);
                setFormData({
                    name: data.name,
                    description: data.description,
                    amountOfEarnings: data.amountOfEarnings,
                    group: data.group || { id: "", name: "" },
                })
                
            }) 
            .catch((error) => console.error("Error fetching chore:", error));
    }, [choreId]);

    const failedMessage = "Oops! Something went wrong while updating the chore.";
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
            navigate(`/chores/${choreId}`); // Navigate to the chore details page
        }
    };

    const handleGroupSelect = (groupName) => {
        const selectedGroup = dummyGroups.find(group => group.name === groupName);
        setFormData((prev) => ({
            ...prev,
            group: selectedGroup || { id: "", name: "" }, // Set group with both id and name
        }));
    };

    return (
        <>
            <form className="chore-form-container">
                <p>Edit Chore</p>
                <div>
                    <TextInputField label="Chore Name" name="name" value={formData.name} setFormData={setFormData} />
                    {errors.name && <p className="error">{errors.name}</p>}
                    <TextAreaInputField label="Chore Description" name="description" value={formData.description} setFormData={setFormData} />
                    <NumericInputField label="Earning Amount, $" name="amountOfEarnings" value={formData.amountOfEarnings} setFormData={setFormData} />
                    {errors.amountOfEarnings && <p className="error">{errors.amountOfEarnings}</p>}
                    <DropdownField label="Group: " options={dummyGroupNames} value={formData.group.name} name="userGroupName"
                     placeholder="Select your group" setFormData={setFormData} onSelect={handleGroupSelect}/>
                    <Button label="Update Chore" onClick={handleSubmit} />
                </div>
            </form>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={handleModalClose} />
        </>
    );
};

export default EditChorePage;
