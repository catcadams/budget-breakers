import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function ModalWindow({ showState, type, message, onClose, onConfirm }) {
    if (!showState) return null;

    const typeClasses = {
        success: { header: "bg-success text-white", button: "btn-success", title: "Success" },
        danger: { header: "bg-danger text-white", button: "btn-danger", title: "Warning" },
        warning: { header: "bg-warning text-dark", button: "btn-warning", title: "Warning" }
    };

    const { header, button, title } = typeClasses[type] || typeClasses.warning;

    return (
        <div className="modal show d-block" tabIndex="-1" role="dialog">
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className={`modal-header ${header}`}>
                        <h5 className="modal-title">{title}</h5>
                        <button type="button" className="btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        <p>{message}</p>
                    </div>
                    <div className="modal-footer">
                        {onConfirm && <button className={`btn ${button}`} onClick={onConfirm}>OK</button>}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ModalWindow;
