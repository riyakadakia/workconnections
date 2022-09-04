import { Button, Card, InputNumber, Row, Col } from 'antd';
import { useState } from 'react';
import axios from "axios";

type Question = {
    name: Array<'1'>;
}

export function InputBoxCard(){
    const [q1, setQ1] = useState<number>();
    const [qNext, setQuestion] = useState<Question>();
    const q1IsValid = q1 !== undefined && q1.toString().length <= 5;
    return (
        <div>
            <div className="site-card-border-less-wrapper">
                <Card title="Input Box" bordered={false} style={{ width: 300 }}>
                <Row style={{ marginBottom: 20 }}>
                    <Col>
                    <InputNumber value={q1} min={(0)} onChange={(value) => setQ1(value)} />
                    </Col>
                </Row>
                <Row>
                    <>
                    {q1IsValid === true &&(
                        <div>Please enter 5 characters</div>
                    )}
                    </>
                </Row>

                </Card>
            </div>
            <Button type="primary" onClick={async () => {
            if (!q1IsValid) {
              return;
            }

            // Send "q1" to the backend
            const response = await axios.get<Question>(`http://localhost:8080/questions/getNextQuestion?surveyId=-1&lastQuestionId=1&lastAnswerIndex=0&lastAnswerInput=${q1}`);

            setQuestion(response.data);
        }}>Next</Button>
            
            </div>
    )
    } 
    ////<Button type='primary' onClick={() => (console.log(q1))} size='large' shape='round'>Next</Button>