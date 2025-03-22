import { useState, useEffect } from "react";
import ReactDOM from 'react-dom/client';
import '../index.css'
import { useNavigate } from "react-router-dom";
import Button from "./Button";

export default function LoginForm () {

    const initialValues = { username: "", password: "" };
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

           const handleSubmit = (event) => {
              event.preventDefault();
              setFormErrors(validate(formValues));
              setIsSubmit(true);
              fetch("http://localhost:8080/user/login", {
                          method: "POST",
                          headers: { "Content-Type": "application/json" },
                          body: JSON.stringify(user)
        })
      };

      const handleChange = (event) => {
        const { name, value } = event.target;
        setFormValues({ ...formValues, [name]: value });
      };

  let navigate = useNavigate();
    const routeChange = () =>{
      let path = `/register`;
      navigate(path);
      };

      useEffect(() => {
          if (Object.keys(formErrors).length === 0 && isSubmit) {
            console.log(formValues);
          }
        }, [formErrors]);

        const validate = (values) => {
          const errors = {};
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
          return errors;
        };

      return (
        <div className="container">
        <form onSubmit={handleSubmit}>
            <h1>Login</h1>
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

                   <Button label="Login" onClick={handleSubmit} />
                   <Button label="Register" onClick={handleSubmit, routeChange} />

                   </form>
                   </div>
                   )
    }

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<LoginForm />);