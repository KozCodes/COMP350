import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const CourseSearch = () => {

    const [query, setQuery] = useState('');

    const navigate = useNavigate();

    const [Days, setDays] = useState(["Empty"]);

    const [Times, setStartTimes] = useState(["Empty"]);

    const [EndTimes, setEndTimes] = useState(["Empty"]);

    const [Session, setSession] = useState('Blank');

    const [Year, setYear] = useState('0000');

    const [CourseCode, setCourseCode] = useState('None');

    const [CourseDepartment, setCourseDepartment] = useState('None');

      const [message, setMessage] = useState('');

      const handleTimeChange = (e) => {
        Times.push(e.target.value);
      }

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
        { value: '14:15:00', label: '14.15.00' },
        { value:'14.50.00', label:'14.50.00'},
        { value: '15:15:00', label: '15:15:00' },
        { value: '15:50:00', label: '15:50:00' },
        { value: '16:15:00', label: '16:15:00' },
        { value: '16:50:00', label: '16.50.00'},
        { value:'17.15.00', label:'17.15.00'},
        { value:'17.50.00', label:'17.50.00'},
        { value:'18.15.00', label:'18.15.00'},
        { value:'18.50.00', label:'18.50.00'},
        { value:'19.15.00', label:'19.15.00'},
        { value:'19.50.00', label:'19.50.00'},
        { value:'20.15.00', label:'20.15.00'},
        { value:'20.50.00', label:'20.50.00'}
    ];

    const handleEndTimeChange = (e) => {
        EndTimes.push(e.target.value);
    }

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

    const handleDaysChange = (id) => {
       //todo
    }

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
<div style={{display: 'flex', flexDirection: 'row', alignItems: 'flex-start', justifyContent: 'space-evenly', gap: '10px', padding: '2rem'}}>
   <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> Home
   </button>
   <button style = {{fontWeight: 'bold', backgroundColor: '#990000', color: 'white'}} onClick={() => navigate('/Home')}> See Schedule

    </button>
</div>

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
           <label>
           <input type="checkbox" id="Monday" name="Monday" value="Monday"/> Monday
           </label>
           <label>
                   <input type="checkbox" id="Tuesday" name="Tuesday" value="Tuesday"/> Tuesday
           </label>
           <label>
                   <input type="checkbox" id="Wednesday" name="Wednesday" value="Wednesday"/> Wednesday
           </label>
              <label>
                     <input type="checkbox" id="Thursday" name="Thursday" value="Thursday"/> Thursday
              </label>
              <label>
                  <input type="checkbox" id="Friday" name="Friday" value="Friday"/> Friday
         </label>
      </div>

      <div style={{display: 'grid', placeItems: 'center'}}>
        <label> Filter by Times </label>
        <select
        name = "startTime"
        onChange = {e => handleTimeChange(e)}
        value={Times}
        >
        <option value= ""> StartTime </option>
        {dropdownStartOptions.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
        </select>
       <select
               name = "endTime"
               onChange = {e => handleTimeChange(e)}
               value={Times}
               >
               <option value= ""> EndTime </option>
               {dropdownEndOptions.map((option) => (
                 <option key={option.value} value={option.value}>
                   {option.label}
                 </option>
               ))}
               </select>
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
                     value={Times}
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
     name="time" value="Course Code"/>
     </div>

     <div style={{display: 'grid', placeItems: 'center'}}>
       <label> Filter by Course Department </label>
       <input type={{}} id="time" onChange = {(e) => setCourseCode(e.target.value)}
       name="time" value="Course Department"/>
     </div>
   </div>
  </div>
 </div>
</div>
</div>
  );
};

export default CourseSearch;
