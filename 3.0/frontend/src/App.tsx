import React from 'react';
import './App.css';
import { Button } from 'antd';
import { useNavigate } from "react-router-dom";
import {
  BrowserRouter as Router,
  Routes,
  Link,
  Route
} from "react-router-dom";
import { About } from './Pages/About';
import { Home } from './Pages/Home';

function App() {
  /*const navigate = useNavigate();*/
  return (
    <div className="App">
      <div className='App-header'>
        <Button type='primary' size='large' shape='round' onClick={(event) => navigateToPage()}>Get started</Button>
      </div>
    </div>
  );
}

export default App;

function navigateToPage() {
  <>
    console.log('inside navigateToPage function');
    <Link to="/">Home</Link>
    <Router>
      <Routes>
          <Route path="/about" element={<About/>} />
          <Route path="/" element={<Home/>} />
      </Routes>
    </Router>
  </>

}