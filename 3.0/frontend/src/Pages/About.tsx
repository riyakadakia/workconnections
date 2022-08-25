import {
    BrowserRouter as Router,
    Link
  } from "react-router-dom";

export function About() {
    return (
        <div>
        <h2>About</h2>
        <nav>
        <ul>
            <li>
            <Link to="/">Home</Link>
            </li>
        </ul>
        </nav>
        </div>
    )
}   