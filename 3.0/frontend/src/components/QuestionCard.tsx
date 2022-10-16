import { Button, Card, Col, Input, Select, Row, Typography, Radio } from "antd";
import { useState } from "react";
import { AnswerWithIndex, Question } from "../types";
import { Some } from "../utils/Some";

type Props = {
  question: Question;

  // TODO: will we need to modify this from "string" or does string support all possible input types
  // number, checkbox, dropdown etc...
  onNext: (answer: string[]) => void;
};

export const QuestionCard = ({ question, onNext }: Props) => {
  const [answer, setAnswer] = useState<AnswerWithIndex>();
  const [validationError, setValidationError] = useState<string>();
  console.log(question);

  return (
    <Card style={{ minWidth: 450 }}>
      <Typography.Title level={3}>{question.text}</Typography.Title>

      <Row className="mb1">
        <Col span={24}>
          {/* TODO: support all question.types possible */}
          {question.type === "text_box" && (
            <Input value={answer} onChange={(e) => setAnswer({e.target.index, e.target.value})} />
          )}
          {question.type === "radio_button" && <Radio value={answer} onChange={(e) => setAnswer({e.target.index, e.target.value})} />}

          {question.type === "drop_down" && (
            <Select style={{ width: "100%" }} onChange={(answer: string) => setAnswer({answer.answerIndex, answer.answerValue})}>
              {question.answer
                .filter((answer) => answer !== "")
                .map((answer) => (
                  <Select.Option key={answer} value={{answer.answerIndex, answer.answerValue}}>
                    {answer}
                  </Select.Option>
                ))}
            </Select>
          )}

          {/* Handle "Checkbox" by using Antd Checkbox.Group */}

          {Some(validationError) && <Typography.Text type="danger">{validationError}</Typography.Text>}
        </Col>
      </Row>

      <Button
        type="primary"
        onClick={() => {
          // TODO: how can we do better validation (like zipCode) at this step?
          if (answer?.answerText === "") {
            setValidationError("Please enter a valid answer");
          } else {
            // If validation passes, we tell the parent component what the answer is
            // Parent component will pop the answer into previousQuestions, and then ask the API for the next question
            // And maybe some other stuff too
            onNext(answer);
          }
        }}
      >
        Next
      </Button>
    </Card>
  );
};
