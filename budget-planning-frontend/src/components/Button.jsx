
const Button = ({ label, onClick }) => {
    return (
        <button
            //type="submit"
            //className={`padding-2 shadow-none hover:shadow background-light-${color} hover:background-dark-${color}`}
            onClick={onClick}>
            {label}
        </button>
    );
};

export default Button; 