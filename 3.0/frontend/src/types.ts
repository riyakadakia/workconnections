export type Question = {
  id: string;
  name: string;
  hint: string;
  type: "text_box";
  format: "number";
  answer: string[];
  nextid: number[];
  text: string;
};

export type QuestionAndAnswer = {
  question: Question;
  answer: unknown;
};
