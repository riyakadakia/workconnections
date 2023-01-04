import { Button } from "antd";
import { Program } from "../types";

type Props = {
  program: Program;
};

export const ProgramCard = ({ program }: Props) => {
  console.log(program);

  return (
    <div className="card-style">
      <div className="program-title-style">{program.name}</div>
      <div className="program-description-style">{program.description}</div>
      <Button id="button-style-id" className="button-style" href={program.url} target="_blank">
        Visit Website
      </Button>
    </div>
  );
};
