import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const CourseSearch = () => {

    const [query, setQuery] = useState('');

    const navigate = useNavigate();

    const [Days, setDays] = useState(['Empty']);

    const [Times, setStartTimes] = useState(['Start Time']);

    const [EndTimes, setEndTimes] = useState(['End Time']);

    const [Session, setSession] = useState('Blank');

    const [Year, setYear] = useState('0000');

    const [CourseCode, setCourseCode] = useState('None');

    const [CourseDepartment, setCourseDepartment] = useState('None');

      const [message, setMessage] = useState('');

      const handleTimeChange = (e) => {
       ;
      }

    const handleEndTimeChange = (e) => {
        ;
    }

    const dropdownDayOptions = [
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday'
    ];



  const handleCheckboxChange = (event) => {
    const value = event.target.value;
    const isChecked = event.target.checked;

    if (isChecked) {
        setDays((prevDays) => [...prevDays, value]);
    } else {
        setDays((prevDays) => prevDays.filter((day) => day !== value));
    }
  };

    const dropdownSessionOptions = [
    { value: 'Fall', label: 'Fall' },
    { value: 'Winter', label: 'Winter' },
    { value: 'Spring', label: 'Spring' },
    { value: 'Early Summer', label: 'Early Summer' },
    { value: 'Late Summer', label: 'Late Summer' },
    ];

    const dropdownYearOptions = [
        { value: '2022', label: '2022' },
        { value: '2023', label: '2023' },
        { value: '2024', label: '2024' },
        { value: '2025', label: '2025' }
    ];



    const handleSessionChange = (e) => {
        setSession(e.target.value);
    }

    const handleYearChange = (e) => {
        setYear(e.target.value);
    }

   const handleSearch = async (e) => {
           try {
               setMessage("We are currently searching for your query...");
              const response = await axios.get(`http://localhost:8080/api/search/${query}/${Days}/${Times}/${EndTimes}/${Session}/${Year}/${CourseCode}/${CourseDepartment}`);
              navigate('/results', {state: response.data});
           } catch (error) {
             setMessage(error.message || 'We\'re sorry, we are unable to parse your request.');
           }
  }

  return (

<div>

<div style={{textAlign: 'center', padding: '4rem', minHeight: '100vh', color: 'black' }}>
   <h2 style = {{ fontWeight: 'bold', color: '#990000'}}> Course Search </h2>

 <div style= {{textAlign: 'center', padding: '2rem'}}>
   <input style = {{}}
   name = "Search..."
   value = {query}
   onChange={(e) => setQuery(e.target.value)}
   required
   />
   <button style= {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}}
        type= "submit"
        onClick={() =>
            handleSearch()}> Search
   </button>
   <h1> {message}</h1>

  <div style={{textAlign: 'center', padding: '2rem', minHeight: '100vh', color: 'black' }}>

    <h1 style= {{color: '#990000'}}> Filter Your Search </h1>
    <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-between', gap: '20px'}}>

       <div style={{display: 'grid', placeItems: 'center'}}>
          <label> Filter by Days </label>
          <h1> {Days}</h1>
                    <label>
                      <input
                        type="checkbox"
                        value="Monday"
                        checked={Days.includes('Monday')}
                        onChange={handleCheckboxChange}
                      />
                      Monday
                    </label>
                      <label>
                                          <input
                                            type="checkbox"
                                            value="Tuesday"
                                            checked={Days.includes('Tuesday')}
                                            onChange={handleCheckboxChange}
                                          />
                                        Tuesday
                                        </label>

                                         <label>
                                             <input
                                             type="checkbox"
                                             value="Wednesday"
                                             checked={Days.includes('Wednesday')}
                                             onChange={handleCheckboxChange}
                                             />
                                             Wednesday
                                         </label>

                                          <label>
                                         <input
                                        type="checkbox"
                                        value="Thursday"
                                        checked={Days.includes('Thursday')}
                                         onChange={handleCheckboxChange}
                                              />
                      Thursday
                                 </label>
                        <label>
                            <input
                            type="checkbox"
                            value="Friday"
                            checked={Days.includes('Friday')}
                            onChange={handleCheckboxChange}
                            />
                            Friday
                        </label>

      </div>

      <div style={{display: 'grid', placeItems: 'center'}}>
        <label> Filter by Times </label>
        <input type={{}} id="time" onChange = {(e) =>  setStartTimes(e.target.value)}
               name="time" value= {Times}/>
               <button onClick= {(e) => handleTimeChange(e)}>Add Start Time</button>
               <input type={{}} id="time" onChange = {(e) => setEndTimes(e.target.value)}
                      name="time" value= {EndTimes}/>
                        <button onClick= {(e) => handleEndTimeChange(e)}>Add End Time</button>
      </div>

      <div style={{display: 'grid', placeItems: 'center'}}>
             <label> Filter by Session </label>
              <select
                           name = "Session"
                           onChange = {e => handleSessionChange(e)}
                           value={Session}
                           >
                           <option value= ""> Session </option>
                           {dropdownSessionOptions.map((option) => (
                             <option key={option.value} value={option.value}>
                               {option.label}
                             </option>
                           ))}
                           </select>
      </div>

     <div style={{display: 'grid', placeItems: 'center'}}>
      <label> Filter by Course Year </label>
        <select
                     name = "Year"
                     onChange = {e => handleYearChange(e)}
                     value={Year}
                     >
                     <option value= ""> Year </option>
                     {dropdownYearOptions.map((option) => (
                       <option key={option.value} value={option.value}>
                         {option.label}
                       </option>
                     ))}
                     </select>
     </div>

     <div style={{display: 'grid', placeItems: 'center'}}>
     <label> Filter by Course Code </label>
     <input type= {{}} id="time"
     onChange = {(e) => setCourseCode(e.target.value)}
     name="time" value= {CourseCode}/>
     </div>

     <div style={{display: 'grid', placeItems: 'center'}}>
       <label> Filter by Course Department </label>
       <input type={{}} id="time" onChange = {(e) => setCourseDepartment(e.target.value) }
       name="time" value= {CourseDepartment}/>
     </div>
   </div>
  </div>
 </div>
</div>
</div>
  );
};

export default CourseSearch;
