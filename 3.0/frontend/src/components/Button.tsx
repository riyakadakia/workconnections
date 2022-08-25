import  "./Button.css";

type Props = {
    text: string;
    size: "large" | "medium" | "small"
    onClick: () => void ;
}

export function Button({ onClick, text, size }: Props) {
    return (
        <button onClick={onClick} className={size}>{text}</button>
    )
} 

/*<Button onClick={() => console.log("Button 1")} size="large" text= "Get Started"/>*/
