import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import "bootstrap/dist/css/bootstrap.min.css";

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

export default App
