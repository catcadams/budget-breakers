import { useState } from "react";
import ReactDOM from 'react-dom/client';
import '../index.css'
import { useNavigate } from "react-router-dom";

export default function LoginForm () {

    const [inputs, setInputs] = useState({});

      const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}))
      }

      const handleSubmit = (event) => {
        event.preventDefault();
        alert(inputs);
      }

  let navigate = useNavigate();
    const routeChange = () =>{
      let path = `/register`;
      navigate(path);
      };

      return (
        <form onSubmit={handleSubmit}>
            <h1>Login</h1>

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

                   <input type="submit" value="Login"></input>
                   <button onClick={routeChange}>Register</button>

                   </form>
                   )
    }

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<LoginForm />);