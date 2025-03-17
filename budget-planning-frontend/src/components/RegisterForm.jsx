import { useState } from "react";
import ReactDOM from 'react-dom/client';
import '../index.css'
import { useNavigate } from "react-router-dom";

export default function RegisterForm () {

    const [inputs, setInputs] = useState({});

      const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}))
      }

      const handleSubmit = (event) => {
        event.preventDefault();
        fetch("http://localhost:8080/user/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(inputs),
                })
            .then(response => response.text())
            .then(data => alert(data))
                    .catch((error) => {
                        alert("error", error);
                    });
            }

let navigate = useNavigate();
  const routeChange = () =>{
    let path = `/login`;
    navigate(path);
    };

      return (
        <form onSubmit={handleSubmit}>
            <h1>Registration</h1>
          <label>First name:
            <input
              type="text"
              name="firstName"
        value={inputs.firstName || ""}
        onChange={handleChange}
            />
          </label>
          <br></br>

          <label>Last name:
                   <input
                   type="text"
                   name="lastName"
                   value={inputs.lastName || ""}
                   onChange={handleChange}
                   />
               </label>
               <br></br>

          <label>Date of birth:
                          <input
                          type="date"
                          name="dateOfBirth"
                          value={inputs.dateOfBirth || ""}
                          onChange={handleChange}
                          />
                      </label>
                      <br></br>

                       <label>Email:
                           <input
                           type="email"
                           name="email"
                           value={inputs.email || ""}
                           onChange={handleChange}
                           />
                       </label>
                       <br></br>

                       <label>Username:
                           <input
                           type="text"
                           name="username"
                           value={inputs.username || ""}
                           onChange={handleChange}
                           />
                       </label>
                       <br></br>

                       <label>Password:
                           <input
                           type="password"
                           name="password"
                           value={inputs.password || ""}
                           onChange={handleChange}
                           />
                       </label>
                       <br></br>

                       <label>Confirm password:
                           <input
                           type="password"
                           name="confirmPassword"
                           value={inputs.confirmPassword || ""}
                           onChange={handleChange}
                           />
                       </label>
                       <br></br>
                         <button onClick={routeChange}>Register</button>
                   </form>
                   )
    }

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<RegisterForm />);