const InputField = ({ label, name, value, setFormData, type }) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    return (
        <div data-mdb-input-init className="mb-4 row">
            <label >{label}:
                <input
                    className="form-control w-25 mx-auto"
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
