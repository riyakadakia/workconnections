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
  return (
    <div className="App">
      <header className="App-header">
        <Router>
          <Routes>
            <Route path="/about" element={<About />} />
            <Route path="/" element={<Home />} />
          </Routes>
        </Router>

      </header>
    </div>
  );
}



export default App;