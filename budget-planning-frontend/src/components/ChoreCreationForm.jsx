import TextInputField from "./TextInputField";
import { useState } from "react";
import NumericInputField from "./NumericInputField";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import TextAreaInputField from "./TextAreaInputField"


const ChoreCreationForm = () => {
    const [formData, setFormData] = useState({ name: "", description: "", amountOfEarnings: "" });
    const [message, setMessage] = useState("");
    const [errors, setErrors] = useState({});
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);

    const failedMessage = "Oops! Something went wrong while creating the chore. Looks like the universe is not ready for this one. Give it another try!";
    const successMessage = "Hooray! Your chore has been successfully created. Now get ready to watch the magic of hard work unfold!";

    let validateUserInputs = () => {
        let errors = {};
        if (!formData.name.trim() ||formData.name.length < 3 || formData.name.length > 50 ) errors.name = "Name is required and must be between 3 and 50 characters.";
        if (!formData.amountOfEarnings || isNaN(formData.amountOfEarnings) || formData.amountOfEarnings < 0) {
            errors.amountOfEarnings = "Amount is required and must be a positive number";
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

    return (
        <form>
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
                <Button label="Create Chore" onClick={handleSubmit} />
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => setShowModal(false)} />
        </form>

    );
};

export default ChoreCreationForm;
