import React from "react";
import { NavLink } from "react-router";
import "./Navbar.css";
import budgetLogo from "../styles/images/logo-transparent-png.png";


function LoginNavbar() {

  return (
    <>
      <div className="nav-bar">
        <nav>
          <img src={budgetLogo} className="logo" alt="Budget Breaker logo" />
          <NavLink to="/Home" className="title">
            Red, Green, VACAY!
          </NavLink>
        </nav>
      </div>
    </>
  );
}

export default LoginNavbar;
