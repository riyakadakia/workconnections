import { Axios } from "axios";
import { config } from "../config";

const makeSurveyClient = () => {
  const httpClient = new Axios({
    baseURL: config.serverUrl,
    headers: {
      "Content-Type": "application/json",
    },
    validateStatus: (status) => status >= 200 && status < 300,
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

  return {
    getTotalProgramsCount,
    startNewSession,
  };
};

export const surveyClient = makeSurveyClient();
