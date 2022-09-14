import { Axios } from "axios";
import { config } from "../config";

const makeSurveyClient = () => {
  const httpClient = new Axios({
    baseURL: config.serverUrl,
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

  return {
    getTotalProgramsCount,
  };
};

export const surveyClient = makeSurveyClient();
