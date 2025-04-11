export const isAdult = (user) => {
    return user?.accountType === "ADULT";
  };

  export const isCurrentUserEqualsAssignedUser = (chore, user) => {
    if (!chore.user || !user) return false;
    return chore.user.id === user.id;
  };