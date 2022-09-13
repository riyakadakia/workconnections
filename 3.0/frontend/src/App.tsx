import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Survey } from "./Survey";
import { Home } from "./Pages/Home";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Router>
          <Routes>
            <Route path="/survey" element={<Survey />} />
            <Route path="/" element={<Home />} />
          </Routes>
        </Router>
      </header>
    </div>
  );
}

export default App;
