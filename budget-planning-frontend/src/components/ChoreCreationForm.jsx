import Name from "./NameInputField";
import Description from "./DescriptionInputField";
import AmountOfEarning from "./AmountOfEarning";
import Create from "./CreateButton";
import InputField from "./InputField";
import { useState } from "react";

const ChoreCreationForm = () => {
  const [formData, setFormData] = useState({ name: "", description: "", amountOfEarning: "" });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    try {
      const response = fetch("http://localhost:8080/chores/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const data = response.text();
        setMessage(data);
      } else {
        setMessage("Failed to create item.");
      }
    } catch (error) {
      setMessage("Error connecting to the server.");
    }
  };

  return (
    <form>
     <div>
      <Name name="name" value={formData.name} onChange={handleChange}/>
      <Description value={formData.description} onChange={handleChange}/>
      <AmountOfEarning value={formData.amountOfEarning} onChange={handleChange}/>
      <Create onClick={handleSubmit} />
    </div>
   </form>
  );
};

export default ChoreCreationForm;
