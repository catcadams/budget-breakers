const InputField = ({ label, name, value, setFormData, type }) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    return (
        <div data-mdb-input-init className="form-outline mb-4">
            <label >{label}:
                <input
                className="form-control"
                    type={type}
                    name={name}
                    value={value}
                    onChange={handleChange}
                />
            </label>
        </div>
    );
};

export default InputField;
