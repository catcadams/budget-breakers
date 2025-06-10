import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Button from "./Button";
import TextInputField from "./TextInputField";
import DateInputField from "./DateInputField";
import EmailInputField from "./EmailInputField";
import PasswordInputField from "./PasswordInputField";
import ModalWindow from "./ModalWindow";

export default function RegisterForm () {

    const initialValues = { firstName: "", lastName: "", dateOfBirth: "", email: "", username: "", password: "", verifyPassword: "" };
    const [formData, setFormData] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [message, setMessage] = useState("");
    const [modalType, setModalType] = useState("success");
    const [showModal, setShowModal] = useState(false);
    const [isSubmit, setIsSubmit] = useState(false);

    const failedMessage =
        "Oops! Something went wrong while registering. Give it another try!";
      const successMessage = "Hooray! You have successfully registered.";

       const handleSubmit = (event) => {
          event.preventDefault();
          setFormErrors(validate(formData));
          setIsSubmit(true);
          };

useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmit) {
      console.log(formData);

      const newUser = {
        ...formData,
      };

      fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(newUser),
      })
        .then((response) => {
          if (response.ok) {
            console.log(newUser);
            setMessage(successMessage);
            setModalType("success");
            setFormData(initialValues);
            setIsSubmit(false);
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
  }, [formErrors, formData, isSubmit]);

        const handleClose = () => {
                setShowModal(false);
                navigate(`/login`);
        }

          let navigate = useNavigate();
            const routeChange = () => {
              let path = `/login`;
              navigate(path);
              };

        const validate = (values) => {
          const errors = {};
          const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
          if (!values.firstName) {
            errors.firstName = "First name is required!";
          }
          if (!values.lastName) {
            errors.lastName = "Last name is required!";
          }
          if (!values.dateOfBirth) {
            errors.dateOfBirth = "Date of birth is required!";
          }
          if (!values.email) {
            errors.email = "Email is required!";
          } else if (!regex.test(values.email)) {
            errors.email = "This is not a valid email format!";
          }
          if (!values.username) {
            errors.username = "Username is required!";
          } else if (values.username.length < 4) {
              errors.username = "Username must be more than 4 characters";
          } else if (values.username.length > 15) {
              errors.username = "Username cannot exceed more than 15 characters";
          }
          if (!values.password) {
            errors.password = "Password is required";
          } else if (values.password.length < 4) {
            errors.password = "Password must be more than 4 characters";
          } else if (values.password.length > 15) {
            errors.password = "Password cannot exceed more than 15 characters";
          }
          if (!values.verifyPassword) {
            errors.verifyPassword = "Password confirmation is required";
          } else if (values.verifyPassword.length < 4) {
            errors.verifyPassword = "Password confirmation must be more than 4 characters";
          } else if (values.verifyPassword.length > 15) {
            errors.verifyPassword = "Password confirmation exceeds more than 15 characters";
          } else if (values.verifyPassword !== values.password) {
             errors.verifyPassword = "Password confirmation does not match password entered."
             }
          return errors;
        };

      return (
        <form onSubmit={handleSubmit}>
            <div className="pageBody">
            <h1>Create New Account</h1>
            <TextInputField label="First name" name="firstName" value={formData.firstName} setFormData={setFormData} />
            <p>{formErrors.firstName}</p>

            <TextInputField label="Last name" name="lastName" value={formData.lastName} setFormData={setFormData} />
            <p>{formErrors.lastName}</p>

            <DateInputField label="Date of birth" name="dateOfBirth" value={formData.dateOfBirth} setFormData={setFormData} />
            <p>{formErrors.dateOfBirth}</p>

            <EmailInputField label="Email" name="email" value={formData.email} setFormData={setFormData} />
            <p>{formErrors.email}</p>

            <TextInputField label="Username" name="username" value={formData.username} setFormData={setFormData} />
            <p>{formErrors.username}</p>

            <PasswordInputField label="Password" name="password" value={formData.password} setFormData={setFormData} />
            <p>{formErrors.password}</p>

            <PasswordInputField label="Confirm password" name="verifyPassword" value={formData.verifyPassword} setFormData={setFormData} />
            <p>{formErrors.verifyPassword}</p>

            <Button label="Register" onClick={handleSubmit} />
            </div>
            <ModalWindow showState={showModal} message={message} type={modalType} onClose={() => handleClose()} />
        </form>
      )
};
