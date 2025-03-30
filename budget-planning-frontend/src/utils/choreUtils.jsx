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
