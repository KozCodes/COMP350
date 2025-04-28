import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const CourseSearch = () => {

    const [query, setQuery] = useState('');

    const navigate = useNavigate();

    const isDisabled = query === '';

    const [Days, setDays] = useState(['Empty']);

    const [Times, setStartTimes] = useState(['Start Time']);

    const [EndTimes, setEndTimes] = useState(['End Time']);

    const [Session, setSession] = useState('Blank');

    const [Year, setYear] = useState('0000');

    const [CourseCode, setCourseCode] = useState('None');

    const [CourseDepartment, setCourseDepartment] = useState('None');

      const [message, setMessage] = useState('');

      const handleTimeChange = (e) => {
          const value = e.target.value;
           if (Times.includes('Start Time')) {
            setStartTimes((prevTimes) => prevTimes.filter((time) => time !== 'Start Time'));
           }
            if (!Times.includes(value)) {
          setStartTimes((prevTimes) => [...prevTimes, value]);
          } else {
            setStartTimes((prevTimes) => prevTimes.filter((time) => time !== value));
          }
      }

    const handleEndTimeChange = (e) => {
         const value = e.target.value;
                        //filter out empty
                        if (EndTimes.includes('End Time')) {
                            setEndTimes((prevEndTimes) => prevEndTimes.filter((endTime) => endTime !== 'End Time'));
                        }
                    if (!EndTimes.includes(value)) {
                        setEndTimes((prevEndTimes) => [...prevEndTimes, value]);
                    } else {
                        setEndTimes((prevEndTimes) => prevEndTimes.filter((endTime) => endTime !== value));
                    }
    }

    const dropdownEndOptions = [
        { value: '08:50:00', label: '08:50:00' },
        { value: '09:15:00', label: '09:15:00' },
        { value: '09:50:00', label: '09:50:00' },
        { value: '10:15:00', label: '10:15:00' },
        { value: '10:50:00', label: '10:50:00' },
        { value: '11:15:00', label: '11:15:00' },
        { value: '11:50:00', label: '11:50:00' },
        { value: '12:15:00', label: '12:15:00' },
        { value: '12:50:00', label: '12:50:00' },
        { value: '13:15:00', label: '13:15:00' },
        { value: '13:50:00', label: '13:50:00' },
        { value: '14:15:00', label: '14:15:00' },
        { value:'14:50:00', label:'14:50:00'},
        { value: '15:15:00', label: '15:15:00' },
        { value: '15:50:00', label: '15:50:00' },
        { value: '16:15:00', label: '16:15:00' },
        { value: '16:50:00', label: '16:50:00'},
        { value:'17:15:00', label:'17:15:00'},
        { value:'17:50:00', label:'17:50:00'},
        { value:'18:15:00', label:'18:15:00'},
        { value:'18:50:00', label:'18:50:00'},
        { value:'19:15:00', label:'19:15:00'},
        { value:'19:50:00', label:'19:50:00'},
        { value:'20:15:00', label:'20:15:00'},
        { value:'20:50:00', label:'20:50:00'}
    ];


    const dropdownStartOptions = [
        { value: '08:00:00', label: '08:00:00' },
        { value: '09:00:00', label: '09:00:00' },
        { value: '09:30:00', label: '09:30:00' },
         { value: '10:00:00', label: '10:00:00' },
        { value: '10:30:00', label: '10:30:00' },
        { value: '11:00:00', label: '11:00:00' },
        { value: '11:30:00', label: '11:30:00' },
        { value: '12:00:00', label: '12:00:00' },
        { value: '12:30:00', label: '12:30:00' },
        { value: '13:00:00', label: '13:00:00' },
        { value: '13:30:00', label: '13:30:00' },
        { value: '14:00:00', label: '14:00:00' },
        { value: '14:30:00', label: '14:30:00' },
        { value: '15:00:00', label: '15:00:00' },
        { value: '15:30:00', label: '15:30:00' },
        { value: '16:00:00', label: '16:00:00' },
        { value: '16:30:00', label: '16:30:00' },
        { value: '17:00:00', label: '17:00:00' },
        { value: '17:30:00', label: '17:30:00' },
        { value: '18:00:00', label: '18:00:00' },
        { value: '18:30:00', label: '18:30:00' },
        { value: '19:00:00', label: '19:00:00' },
        { value: '19:30:00', label: '19:30:00' },
        { value: '20:00:00', label: '20:00:00' },
        { value: '20:30:00', label: '20:30:00' },
        { value: '21:00:00', label: '21:00:00' }
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
        disabled = {isDisabled}
        onClick={() =>
            handleSearch()}> Search

   </button>
   <h1> {message}</h1>

  <div style={{textAlign: 'center', padding: '2rem', minHeight: '100vh', color: 'black' }}>

    <h1 style= {{color: '#990000'}}> Filter Your Search </h1>
    <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-between', gap: '20px'}}>

       <div style={{display: 'grid', placeItems: 'center'}}>
          <label> Filter by Days </label>
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
        <select
                     name = "Start Time"
                     onChange = {e => handleTimeChange(e)}
                     value={Times}
                     >
                     <option value= ""> Start Time </option>
                     {dropdownStartOptions.map((option) => (
                       <option key={option.value} value={option.value}>
                         {option.label}
                       </option>
                     ))}
                     </select>
                     <p> {Times}</p>
                     <button onClick= {(e) => setStartTimes([])}> Clear
                         </button>

        <select
                        name = "End Time"
                        onChange = {e => handleEndTimeChange(e)}
                        value={EndTimes}
                        >
                        <option value= ""> End Time </option>
                        {dropdownEndOptions.map((option) => (
                        <option key={option.value} value={option.value}>
                            {option.label}
                        </option>
                        ))}
                        </select>
                        <p> {EndTimes}</p>
                        <button onClick= {(e) => setEndTimes([])}> Clear </button>

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
