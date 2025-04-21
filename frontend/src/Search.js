import React from "react";
import { useState } from "react";
import axios from "axios";

function Search() {
 const [formData, setFormData] = useState({
    query: ''
  });

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };


}