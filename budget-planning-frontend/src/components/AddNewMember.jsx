import { useState } from "react";
import Button from "./Button";
import ModalWindow from "./ModalWindow";
import { useNavigate } from "react-router-dom";
import useCurrentUser from '../hooks/useCurrentUser';
import { useParams } from "react-router-dom";

const AddNewMember = () => {
        const { user } = useCurrentUser();
        const [formData, setFormData] = useState({emails: [] });
        const [errors, setErrors] = useState({})
        const [message, setMessage] = useState("");
        const [modalType, setModalType] = useState("success");
        const [showModal, setShowModal] = useState(false);
        const { userID, groupID } = useParams();
        //const userID = user?.id;
        const navigate = useNavigate();

        const failedMessage =
            "Oops! Something went wrong while adding new member. Give it another try!";
        const successMessage = "You have successfully invited new members to join your group.";

        const handleSubmit = (event) => {
          event.preventDefault();
          setErrors(validate(formData));
          const dataToSubmit = { ...formData, emails: formData.emails };

            console.log("Submitting emails:", formData.emails);
            fetch(`http://localhost:8080/groups/${userID}/${groupID}/add-member`, {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              credentials: "include",
              body: JSON.stringify(dataToSubmit),
            })
              .then((response) => {
                if (response.ok) {
                  setMessage(successMessage);
                  setModalType("success");
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
                    navigate(`/groups/${userID}`);
                }

              const handleAddEmail = () => {
                      setFormData((prevData) => ({ ...prevData, emails: [...prevData.emails, ""] }));
              };

              const handleEmailChange = (index, value) => {
                const newEmails = [...formData.emails];
                newEmails[index] = value;
                setFormData((prevData) => ({ ...prevData, emails: newEmails }));
              };

              const handleRemoveEmail = (index) => {
                const newEmails = formData.emails.filter((_, i) => i !== index);
                setFormData((prevData) => ({ ...prevData, emails: newEmails }));
                  };

                            const validate = (values) => {
                              const errors = {}
                              const regex = new RegExp("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", "i");
                                values.emails.forEach((email, index) => {
                                if (!regex.test(email)) {
                                errors[`email${index}`] = `Email ${index + 1} is not a valid email!`;
                                }
                                });
                                return errors;
                                };


        return (
            <form onSubmit={handleSubmit}>
                <div className="pageBody">
                <h1>Add New Member</h1>
                <label>Enter emails to invite others to group:</label>
                    {formData.emails.map((email, index) => (
                    <div key={index} className="email-input-container">
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => handleEmailChange(index, e.target.value)}
                            placeholder={`Email ${index + 1}`}
                            required
                        />
                        <button
                            type="button"
                            onClick={() => handleRemoveEmail(index)}
                            className="remove-email"
                        >
                            Remove
                        </button>
                        {errors[`email${index}`] && (
                            <p className="error">{errors[`email${index}`]}</p>
                        )}
                    </div>
                ))}
                <button type="button" onClick={handleAddEmail}>
                    Add email
                </button>
                <button type="submit">Submit</button>
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleClose()} />
            </form>
            )
        };

export default AddNewMember;