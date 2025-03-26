import React, { useState } from "react";
import DropdownButton from "react-bootstrap/DropdownButton";
import Dropdown from "react-bootstrap/Dropdown";

const DropdownField = ({ options, label, name, placeholder, setFormData }) => {
    const [selectedOption, setSelectedOption] = useState(placeholder);

  const handleSelect = (eventKey) => {
    setSelectedOption(eventKey);
	setFormData((prev) => ({ ...prev, [name]: eventKey }));  
  };

  return (
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "10px" }}>
      <h5 style={{ margin: 0 }}>{label}</h5>
      <DropdownButton drop="down" title={selectedOption} className="my-2" onSelect={handleSelect}>
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
