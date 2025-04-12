import React from "react";
import GroupsList from "../GroupsList";
import LoginCheck from "../LoginCheck"

function Groups () {
  return (
    <div>
        <LoginCheck />
        <GroupsList />
    </div>
  )
}

export default Groups;