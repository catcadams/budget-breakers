import { useEffect, useState } from 'react';

const useCurrentUser = () => {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/user', {
      method: 'GET',
      credentials: 'include',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch user');
        }
        return response.json();
      })
      .then((data) => {
        setUser(data);
        console.log("current logged user ID is: " + data.id);
      })
      .catch((err) => {
        setError(err.message);
      });
  }, []);

  return { user, error };
};

export default useCurrentUser;
