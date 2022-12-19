import { Row, Col, Button, Image, Typography } from "antd";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { surveyClient } from "../client/surveyClient";
import { Some } from "../utils/Some";

export function Home() {
  // This state is going to store how many total services (like from the design)
  // Because when the page loads we haven't yet called the API, this state can acutally hold "number | undefined"
  // When you have state that holds "T | undefined" you can just do: useState<T>() and it automatically knows to allow undefined
  const [servicesCount, setServicesCount] = useState<number>();

  // This state will be used to tell if a new session start is underway
  const [isLoading, setIsLoading] = useState(false);

  // We'll need to navigate to the survey page
  const navigate = useNavigate();

  // [useEffect](https://reactjs.org/docs/hooks-effect.html) is the way you call things when a component is mounted
  // OR execute an effect in synchronization with some other state
  // In our case, we want to only execute this function once, when the component is first mounted
  // So we will set the dependency array to be an empty array []
  // NOTE: this is different than setting no dependency at all, because that executed every single render cycle
  useEffect(() => {
    const getServicesCount = async () => {
      const servicesCount = await surveyClient.getTotalProgramsCount();

      setServicesCount(servicesCount);
    };

    getServicesCount();
  }, []);

  // This function will be called when we press Start Survey
  const startSurvey = async () => {
    // Using this isLoading state let's us make the button pretty & spin while we're waiting for the API call to finish
    // It also protects us from double-clicking the button, which would call the API more than once
    setIsLoading(true);

    const sessionId = await surveyClient.startNewSession();
    navigate(`/survey?sessionId=${sessionId}`);

    // Usually we might setIsLoading(false) but actually we don't care because the whole component is unmounting
    // when we navigate to the Survey page
  };

  return (
    <div>
      <Row justify="center" className="mb4">
        <Image width={250} src="image.png" preview={false} alt="WorkConnections" />
      </Row>

      <Row className="mb2">
        <Typography.Title>Find programs that you are eligible for in less than 5 minutes</Typography.Title>

        {/* Only show this paragraph once the servicesCount has been filled in from the API call */}
        {Some(servicesCount) && (
          <Typography.Paragraph type="secondary">
            Currently evaluating {servicesCount} programs for eligibility
          </Typography.Paragraph>
        )}
      </Row>

      <Row justify="center" className="display-block">
        <Button className="button-style" onClick={startSurvey} loading={isLoading}>
          Start
        </Button>
      </Row>
    </div>
  );
}
