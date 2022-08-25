import {
    BrowserRouter as Router,
    Link
  } from "react-router-dom";

export function Home() {
    return (
      <div>
      <h2>Home</h2>
      <nav>
        <ul>
          <li>
            <Link to="/about">About</Link>
          </li>
        </ul>
      </nav>
      </div>
    ) 
}