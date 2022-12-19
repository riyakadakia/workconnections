import { Button } from "antd";
import { Program } from "../types";

type Props = {
  program: Program;
};

export const ProgramCard = ({ program }: Props) => {
  console.log(program);

  return (
    <div className="card-style">
      <div className="question-title-style">{program.name}</div>
      <div>{program.description}</div>
      <Button className="button-style" href={program.url} target="_blank">
        Visit Website
      </Button>
    </div>
  );
};
