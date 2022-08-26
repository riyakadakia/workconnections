import { Checkbox, Row, Card } from 'antd';
import type { CheckboxValueType } from 'antd/es/checkbox/Group';
import React from 'react';

export function CheckBoxCard() {

    const onChange = (checkedValues: CheckboxValueType[]) => {
    console.log('checked = ', checkedValues);
    };
  
  const options = [
    { label: 'Apple', value: 0},
    { label: 'Pear', value: 1 },
    { label: 'Orange', value: 2 },
  ];
    return (
        <div>
            <div className="site-card-border-less-wrapper">
                <Card title="Check Boxes" bordered={false} style={{ width: 300 }}>
                    <Row>
                        <Checkbox.Group options={options} defaultValue={[0]} onChange={onChange} />
                    </Row>

                    <Checkbox.Group
                    defaultValue={[0]}
                    onChange={onChange}
                    />
                </Card>
            </div>
    </div>
    )
    
}  