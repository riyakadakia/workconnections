import { QuestionAndAnswer } from "../types";

type Props = {
  questionAndAnswer: QuestionAndAnswer;
};

export const PreviousQuestion = ({ questionAndAnswer: { question, answer } }: Props) => {
  // TODO: make this pretty, and maybe clickable (if person wants to go back to edit previous questions)
  return (
    <>
      <h4>{question.text}</h4>
      <span>{answer.join(", ")}</span>
    </>
  );
};
