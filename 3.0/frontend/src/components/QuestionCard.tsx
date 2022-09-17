import { Button, Card, Col, Input, Row, Typography } from "antd";
import { useState } from "react";
import { Question } from "../types";
import { Some } from "../utils/Some";

type Props = {
  question: Question;

  // TODO: will we need to modify this from "string" or does string support all possible input types
  // number, checkbox, dropdown etc...
  onNext: (answer: string) => void;
};

export const QuestionCard = ({ question, onNext }: Props) => {
  const [answer, setAnswer] = useState<string>("");
  const [validationError, setValidationError] = useState<string>();

  return (
    <Card style={{ minWidth: 450 }}>
      <Typography.Title level={3}>{question.text}</Typography.Title>

      <Row className="mb1">
        <Col span={24}>
          {/* TODO: support all question.types possible */}
          {question.type === "text_box" && <Input value={answer} onChange={(e) => setAnswer(e.target.value)} />}
          {Some(validationError) && <Typography.Text type="danger">{validationError}</Typography.Text>}
        </Col>
      </Row>

      <Button
        type="primary"
        onClick={() => {
          // TODO: how can we do better validation (like zipCode) at this step?
          if (answer === "") {
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
