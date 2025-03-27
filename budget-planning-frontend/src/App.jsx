import { useState } from 'react'
import './App.css'
import {Routes, Route, Link } from 'react-router-dom';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import ChoreCreationForm from './components/ChoreCreationForm';
import ChoresList from './components/ChoresList';
import SingleChorePage from './components/SingleChorePage'


function App(){

  return(

    <div>
      <Routes>
         <Route path="/login" element={<LoginForm />} />
         <Route path="/register" element={<RegisterForm />} />
        <Route path="/chores/create" element={<ChoreCreationForm />} />
        <Route path="/chores/1/list" element={<ChoresList />} />
        <Route path="/chores/:userGroupId/:choreId" element={<SingleChorePage />} />
      </Routes>
      </div>
  );

}

export default App;