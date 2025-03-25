import "./App.css";
import { Route, Routes } from "react-router-dom";
import Home from "./components/Pages/Home";
import Events from "./components/Pages/Events";
import Chores from "./components/Pages/Chores";
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import Navbar from './components/Navbar'

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/Home" element={<Home />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="/Events" element={<Events />} />
        <Route path="/Chores" element={<Chores />} />
      </Routes>
    </>
  );
}

export default App;
