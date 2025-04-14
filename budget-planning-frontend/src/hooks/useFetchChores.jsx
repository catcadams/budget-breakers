import { useState, useEffect } from "react";
import axios from "axios";
import { isAdult } from "../utils/userUtils";

export const useFetchChores = (groupId) => {
    const [chores, setChores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/chores/${groupId}/list`, { withCredentials: true })
            .then(response => {
                setChores(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load chores");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [groupId]);

    return { chores, loading, error };
};

export const useFetchSingleChore = (groupId, choreId) => {
    const [chore, setChore] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/chores/${groupId}/${choreId}`, { withCredentials: true })
            .then(response => {
                setChore(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load chore details");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [groupId, choreId]);

    return { chore, loading, error };
};

export const useFetchGroupNumber = (groups, loading, user) => {
    const [warningMessage, setWarningMessage] = useState("");
    const [modalType, setModalType] = useState();
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if (loading || !groups || !user) return;

        if(!isAdult(user)) return;

        if (groups.length === 0) {
            setWarningMessage("You need to be part of at least one group to create a chore. Please join a group first!");
            setModalType("warning");
            setShowModal(true);
        }
    }, [groups, loading, user]);

    return {warningMessage, modalType, showModal };

};


