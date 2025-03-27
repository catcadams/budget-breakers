import budgetLogo from '../img/logo-transparent-png.png';

const Banner = () => {
    return (
    <div>
        <img src={budgetLogo} className="logo" alt="Budget Breaker logo" />
        <h1 className="title">Red, Green , VACAY!</h1>
    </div>
     );   
};
export default Banner;