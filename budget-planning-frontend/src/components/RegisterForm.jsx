import { useState, useEffect } from "react";
import ReactDOM from 'react-dom/client';
import '../index.css'
import { useNavigate } from "react-router-dom";
import Button from "./Button";

export default function RegisterForm () {

    const initialValues = { firstName: "", lastName: "", dateOfBirth: "", email: "", username: "", password: "", confirmPassword: "" };
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

    const handleChange = (event) => {
      const { name, value } = event.target;
      setFormValues({ ...formValues, [name]: value });
    };

       const handleSubmit = (event) => {
          event.preventDefault();
          setFormErrors(validate(formValues));
          setIsSubmit(true);
          };

useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmit) {
      console.log(formValues);

      const newUser = {
        ...formValues,
      };

      fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formValues),
      })
        .then((response) => {
          if (response.ok) {
            console.log("User successfully registered");
            console.log(newUser);
            setFormValues(initialValues); // Reset form values after successful submit
            setIsSubmit(false); // Reset submit flag
          } else {
            console.error("Registration failed");
          }
        })
        .catch((error) => console.error("Error:", error));
    }
  }, [formErrors, formValues, isSubmit]);

          let navigate = useNavigate();
            const routeChange = () =>{
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
          if (!values.confirmPassword) {
            errors.confirmPassword = "Password confirmation is required";
          } else if (values.confirmPassword.length < 4) {
            errors.confirmPassword = "Password confirmation must be more than 4 characters";
          } else if (values.confirmPassword.length > 15) {
            errors.confirmPassword = "Password confirmation exceeds more than 15 characters";
          }
          return errors;
        };

      return (
        <div className="container">
        <form onSubmit={handleSubmit}>
            <h1>Registration</h1>
          <label>First name:
            <input
              type="text"
              name="firstName"
              value={formValues.firstName}
              onChange={handleChange}
            />
          </label>
          <p>{formErrors.firstName}</p>

          <label>Last name:
                   <input
                   type="text"
                   name="lastName"
                   value={formValues.lastName}
                   onChange={handleChange}
                   />
               </label>
               <p>{formErrors.lastName}</p>

          <label>Date of birth:
                          <input
                          type="date"
                          name="dateOfBirth"
                          value={formValues.dateOfBirth}
                          onChange={handleChange}
                          />
                      </label>
                      <p>{formErrors.dateOfBirth}</p>

                       <label>Email:
                           <input
                           type="email"
                           name="email"
                           value={formValues.email}
                           onChange={handleChange}
                           />
                       </label>
                       <p>{formErrors.email}</p>

                       <label>Username:
                           <input
                           type="text"
                           name="username"
                           value={formValues.username}
                           onChange={handleChange}
                           />
                       </label>
                       <p>{formErrors.username}</p>

                       <label>Password:
                           <input
                           type="password"
                           name="password"
                           value={formValues.password}
                           onChange={handleChange}
                           />
                       </label>
                       <p>{formErrors.password}</p>

                       <label>Confirm password:
                           <input
                           type="password"
                           name="confirmPassword"
                           value={formValues.confirmPassword}
                           onChange={handleChange}
                           />
                       </label>
                       <p>{formErrors.confirmPassword}</p>
                         <Button label="Register" onClick={handleSubmit} />
                         <Button label="Login" onClick={handleSubmit, routeChange} />
                   </form>
                   </div>
                   )
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<RegisterForm />);