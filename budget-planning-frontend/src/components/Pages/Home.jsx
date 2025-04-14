import React from 'react'
import LoginCheck from "../LoginCheck.jsx"
import useCurrentUser from '../../hooks/useCurrentUser.jsx';
import MainPage from '../MainPage.jsx';

function Home() {
  const { user, error: userError } = useCurrentUser();

  return (
    <div>
      {user ? <MainPage /> : <LoginCheck />}
    </div>
  );
}

export default Home
