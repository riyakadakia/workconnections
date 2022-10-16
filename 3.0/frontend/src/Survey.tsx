import { Row, Spin } from "antd";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { surveyClient } from "./client/surveyClient";
import { PreviousQuestion } from "./components/PreviousQuestion";
import { QuestionCard } from "./components/QuestionCard";
import "./Survey.css";
import { Question, QuestionAndAnswer } from "./types";
import { None } from "./utils/None";
import { Some } from "./utils/Some";

export function Survey() {
  console.log("Rendering Survey()");

  // Let's ensure we have a valid sessionId as a query string parameter, and if not, probably just go to the home page
  const navigate = useNavigate();
  const sessionId = new URLSearchParams(window.location.search).get("sessionId");
  const [surveyId, setSurveyId] = useState<number>(0);

  if (None(sessionId) || sessionId === "") {
    navigate("/");
  }

  // I'm not sure how we're going to store the dynamic structure of previous questions & answers o.O
  // This is just a first guess
  const [previousQuestions, setPreviousQuestions] = useState<QuestionAndAnswer[]>([]);

  // This state will be used to hold the currently displayed question
  // I wasn't expecting this to be a dynamic one passed from the server, we'll have to figure out how to tell what validation
  // to use for each question, and how to render the input type
  const [currentQuestion, setCurrentQuestion] = useState<Question>();

  // We want to get the next question to display, and synchronize it to the previousQuestions.length state
  // That way, if we add (or remove) questions from previousQuestions array, we will call this effect again that
  // should make sure we're always displaying the right question on screen
  useEffect(() => {
    console.log("previousQuestions.length: ", previousQuestions.length);

    const getNextQuestion = async () => {
      // TODO: call "addToSession"
      // TODO: Update eligible programs count

      // Depending on how many questions are in the previousQuestions we need to behave differently
      if (previousQuestions.length === 0) {
        const question = await surveyClient.getFirstQuestion();
        setCurrentQuestion(question);
      }

      if (previousQuestions.length === 1) {
        if (Some(sessionId)) {
          const sid = await surveyClient.getSurveyIdFromZip(sessionId, previousQuestions[0].answer[0]);
          setSurveyId(sid);

          const question = await surveyClient.getSecondQuestion(
            previousQuestions.length,
            previousQuestions[0].answer[0]
          );

          setCurrentQuestion(question);
        } else {
          throw new Error("Somehow attempting to getSurveyIdFromZip but sessionId is null");
        }
      }

      if (previousQuestions.length > 1) {
        const question = await surveyClient.getNextQuestion(
          previousQuestions.length,
          surveyId,

          // TODO: correctly figure out the index here
          -1,

          previousQuestions[previousQuestions.length - 1].answer[0]
        );
        setCurrentQuestion(question);
      }

      // TODO: implement what happens when previousQuestions.length > 0
      // It might need to call a differeny surveyClient function, passing in some parameters
      // It must still update currentQuestion

      // TODO: how do we determine if we've finished the current questions, and what happens then?
    };

    getNextQuestion();
  }, [previousQuestions, previousQuestions.length, sessionId, surveyId]);

  return (
    <>
      {/* This will be the "Previous questions" bit from the design */}
      {previousQuestions.map((q) => (
        <PreviousQuestion key={q.question.id} questionAndAnswer={q} />
      ))}

      {/* Here, we will display the currentQuestion (if any loaded up yet) */}
      {Some(currentQuestion) ? (
        <QuestionCard
          question={currentQuestion}
          onNext={(answer) => {
            // Store the previous question away with it's answer
            setPreviousQuestions((prev) => [
              ...prev,
              {
                question: currentQuestion,
                answer,
              },
            ]);

            // And clear the current question
            // NOTE: we'll get a new one from the API automatically thanks to the useEffect watching the length of previousQuestions array
            setCurrentQuestion(undefined);
          }}
        />
      ) : (
        <Row justify="center" align="middle" className="mt1 mb1">
          <Spin size="large" />
        </Row>
      )}
    </>
  );
}
