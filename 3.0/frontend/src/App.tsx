import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Survey } from "./Survey";
import { Home } from "./Pages/Home";
import { Col, Row } from "antd";

function App() {
  return (
    <Row className="App">
      <Col>
        <Router>
          <Routes>
            <Route path="/survey" element={<Survey />} />
            <Route path="/" element={<Home />} />
          </Routes>
        </Router>
      </Col>
    </Row>
  );
}

export default App;
