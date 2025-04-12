import { useState, useEffect } from "react";
import axios from "axios";

export const useFetchGroups = (userID) => {
    const [groups, setGroups] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if(userID === -1) return;
        axios.get(`http://localhost:8080/groups/${userID}/list`, { withCredentials: true })
            .then(response => {
                setGroups(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load groups");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [userID]);

    return { groups, loading, error };
};

export const useFetchSingleGroup = (userID, groupID) => {
    const [group, setGroup] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/groups/${userID}/${groupID}`, { withCredentials: true })
            .then(response => {
                setGroup(response.data);
                setError(null);
            })
            .catch(err => {
                setError("Failed to load group details");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, [userID, groupID]);

    return { group, loading, error };
};  

