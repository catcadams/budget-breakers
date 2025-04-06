import { useState, useEffect } from "react";
import axios from "axios";

export const useFetchChores = (userGroupId) => {
    const [chores, setChores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/chores/${userGroupId}/list`, { withCredentials: true })
            .then(response => {
                setChores(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load chores");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [userGroupId]);

    return { chores, loading, error };
};

export const useFetchSingleChore = (userGroupId, choreId) => {
    const [chore, setChore] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/chores/${userGroupId}/${choreId}`, { withCredentials: true })
            .then(response => {
                setChore(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load chore details");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [userGroupId, choreId]);

    return { chore, loading, error, choreStatus };
};


