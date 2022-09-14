import { Row, Col, Button, Image, Typography } from "antd";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { surveyClient } from "../client/surveyClient";
import { Some } from "../utils/Some";

export function Home() {
  const [servicesCount, setServicesCount] = useState<number>();

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

  return (
    <Row justify="center">
      <Col span={8}>
        <Row justify="center" className="mb4">
          <Col>
            <Image width={157} src="https://placekitten.com/157/157" preview={false} alt="A cute kitten" />
          </Col>
        </Row>

        <Row className="mb2">
          <Col>
            <Typography.Title>Find what services you are eligible for in less than 5 minutes</Typography.Title>

            {/* Only show this paragraph once the servicesCount has been filled in from the API call */}
            {Some(servicesCount) && (
              <Typography.Paragraph type="secondary">Currently listing {servicesCount} services</Typography.Paragraph>
            )}
          </Col>
        </Row>

        <Row justify="center">
          <Col>
            <Link to="/survey">
              <Button type="primary" size="large" shape="round">
                Start
              </Button>
            </Link>
          </Col>
        </Row>
      </Col>
    </Row>
  );
}
