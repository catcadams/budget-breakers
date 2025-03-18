import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function ModalWindow({ showState, type, message, onClose }) {
    if(!showState){return null;}

    const headerClass = type === "success" ? "bg-success text-white" : "bg-danger text-white";
    const buttonClass = type === "success" ? "btn-success" : "btn-danger";

    return (
        <div className="modal show d-block" tabIndex="-1" role="dialog">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className={`modal-header ${headerClass}`}>
                        <h5 className="modal-title">{type === "success" ? "Success" : "Error"}</h5>
                        <button type="button" className="btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        <p>{message}</p>
                    </div>
                    <div className="modal-footer">
                        <button className={`btn ${buttonClass}`} onClick={onClose}>OK</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ModalWindow;
