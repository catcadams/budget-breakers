import InputField from "./InputField";

const NumericInputField = ({ label, name, value, setFormData }) => {
    return <InputField 
    label={label} name={name} value={value} setFormData={setFormData} type="number" />;
};

export default NumericInputField;