import { Row, Col, Button, Image, Typography } from "antd";
import { Link } from "react-router-dom";

export function Home() {
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
            <Typography.Paragraph type="secondary">Currently listing X services</Typography.Paragraph>
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
