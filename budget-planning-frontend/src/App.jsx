import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import ChoreCreationForm from './components/ChoreCreationForm';

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div className="App">
	  <ChoreCreationForm />
      </div>
    </>

  )
}

export default App
