import { useState } from 'react'
import './App.css'
import "bootstrap/dist/css/bootstrap.min.css";
import ChoreCreationForm from './components/ChoreCreationForm';
import Banner from "./components/Banner"
import CreateEvent from './components/CreateEvent';
import Navbar from './components/Navbar'
import Groups from './Pages/Groups'
import Events from './Pages/Events'
import Chores from './Pages/Chores'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    <Banner />
    <CreateEvent />
    </>

  )
}

export default App
/*<BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/groups" element={<Groups />} />
        <Route path="/events" element={<Events />} />
        <Route path="/chores" element={<Chores />} />
      </Routes>
      </BrowserRouter> */