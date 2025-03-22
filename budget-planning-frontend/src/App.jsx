import { useState } from 'react'
import './App.css'
import { Routes, Route, Link } from 'react-router-dom';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import { BrowserRouter } from 'react-router-dom'


function App(){

  return(
          <>
              <Routes>
                  <Route path="/register" element={<RegisterForm />} />
                  <Route path="/login" element={<LoginForm />} />
                  </Routes>
            </>
  );
}

export default App;