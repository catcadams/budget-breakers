import React from 'react'
import { NavLink } from 'react-router'
import "./Navbar.css"
import budgetLogo from '../img/logo-transparent-png.png';
import NavDropdown from "react-bootstrap/NavDropdown";

function Navbar() {
  return (
    <>
    
    <div class="nav-bar">  
      <nav>
         <img src={budgetLogo} className="logo" alt="Budget Breaker logo" />
        <NavLink to="/" className='title'> Red, Green, VACAY!
        </NavLink>
        <div className='menu'>
            <span></span>
            <span></span>
            <span></span>
        </div>
        <ul>
            <li><NavLink to="/Home">Home </NavLink></li>
            <li>
                <NavLink to="/Events">Events</NavLink>
            </li>
            <li><NavLink to="/Chores"> Chores</NavLink></li>
        </ul>
      </nav>
    </div>
    </>
  )
}

export default Navbar
