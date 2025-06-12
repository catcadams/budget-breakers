import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useCurrentUser from "../hooks/useCurrentUser";

import Button from "./Button";
import GroupsList from "./GroupsList";

const UserAccount = () => {
  const { user, error: userError } = useCurrentUser();
  const userID = user?.id;

  let navigate = useNavigate();

  if (userError) return <div>{userError}</div>;

  return (
    <>
      <div className="heading">
        <h2 className="account-title">Account Center</h2>
        <p className="name">{user?.firstName} {user?.lastName}</p>
        <p className="username">Username: {user?.username}</p>
      </div>
      <div className="account-body">
        <div className="tiles-container">
          <h3>My Groups</h3>
          <GroupsList />
        </div>
        <div className="piggy-bank">
          <h3>My Savings</h3>
        </div>
      </div>
    </>
  );
};

export default UserAccount;
