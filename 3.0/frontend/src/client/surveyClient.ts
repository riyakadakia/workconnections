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

  // Not sure if this "getFirstQuestion" will survive as we evolve the API client, but it feels like an easy way to start
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
  const getSecondQuestion = async (questionNumber: any) => {
    const queryParams = new URLSearchParams({
      surveyId: "-1",
      lastQuestionId: questionNumber,
      lastAnswerIndex: "0",
      lastAnswerInput: "70801",
    });

    const { data: secondQuestion } = await httpClient.get<Question>(`questions/getNextQuestion?${queryParams}`);

    return secondQuestion;
  };

  return {
    getFirstQuestion,
    getTotalProgramsCount,
    startNewSession,
    getSecondQuestion,
  };
};

export const surveyClient = makeSurveyClient();
