import './Survey.css';
import  {InputBoxCard} from './components/InputBox';
import { RadioButtonCard } from './components/RadioButtons';
import { CheckBoxCard } from './components/CheckBoxes';
import type { CheckboxValueType } from 'antd/es/checkbox/Group';

var Qu1 = 2;
var answer: { Qu1: number; };
export function Survey(){
    const onChange = (checkedValues: CheckboxValueType[]) => {
        console.log('checked = ', checkedValues);
        answer = entire()
        };
    
        function entire(){
            while (Qu1 != -1){
                console.log('in')
                Qu1 = Qu1 - 1;
                <CheckBoxCard data={["option1","option2","option3"]} onChange={onChange}/>
            }
            return{
                Qu1
            }
        }    

    return (
        answer
        //<CheckBoxCard data={["option1","option2","option3"]} onChange={onChange}/>
        //<InputBoxCard/>
        //<CheckBoxCard/>
        //CheckBoxCard()
        //InputBoxCard()
        //RadioButtonCard()
    )   
}   

/*<div>
function entire(){
            console.log('in')
        }
        
            <CheckBoxCard data={["option1","option2","option3"]} onChange={onChange}/>
        </div>*/

