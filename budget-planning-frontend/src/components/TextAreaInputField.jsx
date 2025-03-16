const TextAreaInputField = ({ label, name, value, setFormData }) => {
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    return (
        <div data-mdb-input-init className="form-outline mb-4">
            <label>{label}:
                <textarea
                    className="form-control"
                    name={name}
                    value={value}
                    onChange={handleChange}
                    maxLength={250}
                    rows="5"
                />
            </label>
            <div>
                <small>{250 - value.length} characters remaining</small>
            </div>

        </div>
    );
};

export default TextAreaInputField;
