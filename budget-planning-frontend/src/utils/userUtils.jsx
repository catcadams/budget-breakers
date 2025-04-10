export const isAdult = (user) => {
    if(user?.accountType === "ADULT")
      sessionStorage.setItem("isAdult", true);
    return user?.accountType === "ADULT";
  };