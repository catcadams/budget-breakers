import available from "../styles/images/available.jpg";
import completed from "../styles/images/completed.jpg";
import foldedShirts from "../styles/images/foldedShirts.png";
import inprogress from "../styles/images/inprogress.jpg";
import pending from "../styles/images/pending.jpg";

export const getChoreImage = (status) => {
    switch (status) {
        case "COMPLETE":
            return completed;
        case "IN_PROGRESS":
            return inprogress;
        case "OPEN":
            return available;
        case "PENDING":
            return pending;
        default:
            return foldedShirts; 
    }
};

export const isChoreEditableOrDeletable = (status) => status === "OPEN";


export const getChoreStatusMessage = (chore) => {
    switch (chore.status) {
      case "OPEN":
        return "This chore is looking for its hero!";
      case "IN_PROGRESS":
        return `${chore.user.firstName} is working on it now!`;
      case "PENDING":
        return `${chore.user.firstName} gave it their all! Time to inspect the masterpiece and give it the final thumbs-up!`;
      case "COMPLETE":
        return "This chore is officially in the hall of fame! No further action needed.";
      default:
        return null;
    }
  };

  export const isEventBudgetReached = (event) => event.budgetReached === false;
