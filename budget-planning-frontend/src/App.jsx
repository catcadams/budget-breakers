import { Routes ,Route} from "react-router";
import "./App.css";
import Banner from "./components/Banner";
import CreateEvent from "./components/CreateEvent";
import Navbar from "./components/Navbar";
import NavbarUI from "./components/NavbarUI";
import Home from "./components/Pages/Home";
import Events from "./components/Pages/Events";
import Chores from "./components/Pages/Chores";

function App() {
  return (
    <>
      <Navbar />
      <Routes>
      <Route path="/Home" element={<Home />} />
        <Route path="/Events" element={<Events />} />
        <Route path="/Chores" element={<Chores />} />
      </Routes>
    </>
  );
}

export default App;
