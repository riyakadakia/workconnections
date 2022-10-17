import axios from "axios";
import { config } from "../config";
import { Question } from "../types";

const makeSurveyClient = () => {
  const httpClient = axios.create({
    baseURL: config.serverUrl,
  });

  // Fail if API doesn't return 2xx
  httpClient.interceptors.response.use(undefined, (error) => {
    window.alert("Something has gone terribly wrong :(\n\n" + error);

    throw error;
  });

  const getTotalProgramsCount = async () => {
    const { data: servicesCount } = await httpClient.get<number>("programs/getTotalProgramsCount");

    return servicesCount;
  };

  const startNewSession = async () => {
    const { data: sessionId } = await httpClient.post<string>("sessions/createSession");

    return sessionId;
  };

  const getFirstQuestion = async () => {
    const queryParams = new URLSearchParams({
      surveyId: "-1",
      lastQuestionId: "-1",
      lastAnswerIndex: "-1",
      lastAnswerInput: "",
    });

    const { data: firstQuestion } = await httpClient.get<Question>(`questions/getNextQuestion?${queryParams}`);

    return firstQuestion;
  };

  const getSurveyIdFromZip = async (sessionId: string, zip: string) => {
    const queryParams = new URLSearchParams({
      sessionId,
      zip,
    });

    const { data: surveyId } = await httpClient.get<number>(`sessions/getSurveyIdFromZip?${queryParams}`);

    return surveyId;
  };

  const getSecondQuestion = async (questionNumber: number, zip: string) => {
    const queryParams = new URLSearchParams({
      surveyId: "-1",
      lastQuestionId: questionNumber.toString(),
      lastAnswerIndex: "0",
      lastAnswerInput: zip,
    });

    const { data: secondQuestion } = await httpClient.get<Question>(`questions/getNextQuestion?${queryParams}`);

    return secondQuestion;
  };

  // TODO: Fix the params of this const
  const getNextQuestion = async (questionNumber: number, sid: number, lai: number, lain: string) => {
    const queryParams = new URLSearchParams({
      surveyId: sid.toString(),
      lastQuestionId: questionNumber.toString(),
      lastAnswerIndex: lai.toString(),
      lastAnswerInput: lain,
    });

    const { data: nextQuestion } = await httpClient.get<Question>(`questions/getNextQuestion?${queryParams}`);

    return nextQuestion;
  };

  return {
    getFirstQuestion,
    getTotalProgramsCount,
    startNewSession,
    getSecondQuestion,
    getNextQuestion,
    getSurveyIdFromZip,
  };
};

export const surveyClient = makeSurveyClient();
