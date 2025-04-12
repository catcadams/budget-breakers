
const Button = ({ label, onClick, className }) => {
    return (
        <button className={className} type="button"
            onClick={onClick}>
            {label}
        </button>
    );
};

export default Button; 