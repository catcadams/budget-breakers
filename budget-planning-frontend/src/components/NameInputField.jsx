const NameInputField = ({ name, value, onChange }) => {
    return (
      <label>
        Name:
        <input type="text" name={name} value={value} onChange={onChange} className="border p-2 w-full rounded" required />
      </label>
    );
  };
  export default NameInputField;

