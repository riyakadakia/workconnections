import { Button, Card, Typography } from "antd";
import { Program } from "../types";

type Props = {
  program: Program;
};

export const ProgramCard = ({ program }: Props) => {
  console.log(program);

  return (
    <Card style={{ minWidth: 450 }}>
      <Typography.Title level={3}>{program.name}</Typography.Title>
      <div>{program.description}</div>
      <Button type="primary" href={program.url} target="_blank">
        Visit Website
      </Button>
    </Card>
  );
};
