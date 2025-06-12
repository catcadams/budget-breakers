import React from "react";
import { NavLink } from "react-router";
import "./Navbar.css";
import budgetLogo from "../styles/images/logo-transparent-png.png";

function Navbar() {
  return (
    <>
      <div className="nav-bar">
        <nav>
          <img src={budgetLogo} className="logo" alt="Budget Breaker logo" />
          <NavLink to="/Home" className="title">
            Red, Green, VACAY!
          </NavLink>
            <ul>
              <li>
                <NavLink to="/Home">Home </NavLink>
              </li>
              <li>
                <NavLink to="/Groups">Groups</NavLink>
              </li>
              <li>
                <NavLink to="/Events">Events</NavLink>
              </li>
              <li>
                <NavLink to="/Chores">Chores</NavLink>
              </li>
              <li>
                <NavLink to="/user">Account Center</NavLink>
              </li>
              <li>
                <NavLink to="/Logout">Logout</NavLink>
              </li>
            </ul>
        </nav>
      </div>
    </>
  );
}

export default Navbar;
