import {
    BrowserRouter as Router,
    Link
  } from "react-router-dom";
import { Button } from 'antd';

export function Home() {
    return (
      <div>
      <h2>Home</h2>
      <Button type='primary' href="/about" size='large' shape='round'>About</Button>

      </div>
    ) 
}