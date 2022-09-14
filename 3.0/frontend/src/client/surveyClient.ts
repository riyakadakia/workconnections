import { Axios } from "axios";
import { config } from "../config";

const makeSurveyClient = () => {
  const httpClient = new Axios({
    baseURL: config.serverUrl,
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
