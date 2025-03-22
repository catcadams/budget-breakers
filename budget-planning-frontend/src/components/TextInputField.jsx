import InputField from "./InputField";

const TextInputField = ({ label, name, value, setFormData }) => {
    return <InputField label={label} name={name} value={value} setFormData={setFormData} type="text" />;
};

export default TextInputField;