import { Button } from "antd";

export function Home() {
  return (
    <div>
      <h2>Find what services you are eligible for in less than 5 minutes</h2>

      <Button type="primary" href="/survey" size="large" shape="round">
        Start
      </Button>
    </div>
  );
}
