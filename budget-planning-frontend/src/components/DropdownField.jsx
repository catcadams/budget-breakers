import React, { useState } from "react";
import DropdownButton from "react-bootstrap/DropdownButton";
import Dropdown from "react-bootstrap/Dropdown";
import { useEffect } from "react";

const DropdownField = ({ options, label, name, placeholder, setFormData, value, handleSelect }) => {
  const [selectedOption, setSelectedOption] = useState(placeholder);

  const handleSelectCommon = (eventKey) => {
    setSelectedOption(eventKey);
    setFormData((prev) => ({ ...prev, [name]: eventKey }));
  };

  useEffect(() => {
    if (value) {
      setSelectedOption(value);
    }
  }, [value]);

  return (
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "10px" }}>
      <h5 style={{ margin: 0 }}>{label}</h5>
      <DropdownButton drop="down" title={selectedOption} className="my-2" onSelect={handleSelect ? handleSelect : handleSelectCommon}>
        {options.map((option, index) => (
          <Dropdown.Item key={index} eventKey={option}>
            {option}
          </Dropdown.Item>
        ))}
      </DropdownButton>
    </div>
  );
};

export default DropdownField;
