import React, { useState, useEffect } from "react";
    import axios from "axios";
    import { DndContext, closestCenter } from "@dnd-kit/core";
    import { arrayMove, SortableContext, useSortable, verticalListSortingStrategy } from "@dnd-kit/sortable";
    import { CSS } from "@dnd-kit/utilities";

    const SortableItem = ({ id, name, onChange, allCourses, removeCourse }) => {
      const { attributes, listeners, setNodeRef, transform, transition } = useSortable({ id });

      const style = {
        transform: CSS.Transform.toString(transform),
        transition,
        display: "flex",
        alignItems: "center",
        padding: "8px",
        margin: "4px 0",
        backgroundColor: "#f8f9fa",
        border: "1px solid #ced4da",
        borderRadius: "4px",
      };

      const grabberStyle = {
        cursor: "grab",
        marginRight: "8px",
        padding: "0 8px",
        backgroundColor: "#e9ecef",
        borderRadius: "4px",
      };

      const removeButtonStyle = {
        marginLeft: "8px",
        backgroundColor: "#ff4d4d",
        color: "white",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
        padding: "4px 8px",
      };

      return (
        <div ref={setNodeRef} style={style} {...attributes}>
          <div {...listeners} style={grabberStyle}>
            &#x2630;
          </div>
          <input
            type="text"
            value={name}
            onChange={(e) => onChange(e.target.value)}
            list={`courseSuggestions-${id}`}
            style={{ flex: 1, padding: "8px", border: "none", outline: "none" }}
          />
          <datalist id={`courseSuggestions-${id}`}>
            {allCourses.map((course, index) => (
              <option key={index} value={course} />
            ))}
          </datalist>
          <button style={removeButtonStyle} onClick={() => removeCourse(id)}>
            X
          </button>
        </div>
      );
    };

    const SchedulerPage = () => {
      const [courses, setCourses] = useState(() => {
        const savedCourses = localStorage.getItem("courses");
        return savedCourses ? JSON.parse(savedCourses) : [{ id: 1, name: "" }];
      });
      const [allCourses, setAllCourses] = useState([]);
      const [year, setYear] = useState(() => localStorage.getItem("year") || "");
      const [session, setSession] = useState(() => localStorage.getItem("session") || "");
      const [error, setError] = useState("");
      const [schedule, setSchedule] = useState(null);
      const [conflicts, setConflicts] = useState(null);
      const [scheduleLink, setScheduleLink] = useState(null);

      useEffect(() => {
        const fetchCourses = async () => {
          try {
            const response = await axios.get("http://localhost:8080/api/allAmbiguousCourses");
            const sortedCourses = response.data.sort((a, b) => a.localeCompare(b));
            setAllCourses(sortedCourses);
          } catch (error) {
            console.error("Error fetching courses:", error);
          }
        };

        fetchCourses();
      }, []);

      useEffect(() => {
        localStorage.setItem("courses", JSON.stringify(courses));
      }, [courses]);

      useEffect(() => {
        localStorage.setItem("year", year);
      }, [year]);

      useEffect(() => {
        localStorage.setItem("session", session);
      }, [session]);

      const handleInputChange = (index, value) => {
        const updatedCourses = [...courses];
        updatedCourses[index].name = value;
        setCourses(updatedCourses);
      };

      const addCourseField = () => {
        setCourses([...courses, { id: courses.length + 1, name: "" }]);
      };

      const removeCourse = (id) => {
        setCourses(courses.filter((course) => course.id !== id));
      };

      const handleDragEnd = (event) => {
        const { active, over } = event;

        if (active.id !== over.id) {
          const oldIndex = courses.findIndex((course) => course.id === active.id);
          const newIndex = courses.findIndex((course) => course.id === over.id);
          setCourses((prevCourses) => arrayMove(prevCourses, oldIndex, newIndex));
        }
      };

      const generateSchedule = async () => {
        try {
          const response = await axios.post("http://localhost:8080/api/generateSchedule", {
            enteredCourses: courses.map((course) => course.name),
            session,
            year: parseInt(year, 10),
          });

          if (response.data && typeof response.data === "string" && response.data.startsWith("/schedule/")) {
            setScheduleLink(response.data);
            setError("");
          } else if (Array.isArray(response.data) && response.data.length > 0) {
            if (response.data[0].hasOwnProperty("id")) {
              setSchedule(response.data);
              setConflicts(null);
              setError("");
            } else {
              setConflicts(response.data);
              setSchedule(null);
              setError("");
            }
          } else {
            setSchedule(null);
            setConflicts(null);
            setError("Unexpected response format.");
          }
        } catch (err) {
          setError("Error generating schedule.");
          setSchedule(null);
          setConflicts(null);
        }
      };

      return (
        <div style={{
          backgroundColor: "#f4f4f4",
          minHeight: "100vh",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          flexDirection: "column",
          padding: "2rem"
        }}>
          <div style={{ textAlign: "center", marginBottom: "2rem" }}>
            <h1 style={{ fontSize: "2.5rem", fontWeight: "bold", color: "#990000", marginBottom: "1rem" }}>
              Scheduler Page
            </h1>
            <p style={{ fontSize: "1.25rem", color: "#333", marginBottom: "2rem" }}>
              Enter potential courses and list them in order of preference.
            </p>
          </div>

          <div style={{
            backgroundColor: "white",
            borderRadius: "16px",
            padding: "30px",
            width: "100%",
            maxWidth: "600px",
            boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)"
          }}>
            <DndContext collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
              <SortableContext items={courses} strategy={verticalListSortingStrategy}>
                {courses.map((course, index) => (
                  <SortableItem
                    key={course.id}
                    id={course.id}
                    name={course.name}
                    onChange={(value) => handleInputChange(index, value)}
                    allCourses={allCourses}
                    removeCourse={removeCourse}
                  />
                ))}
              </SortableContext>
            </DndContext>

            <button onClick={addCourseField} style={buttonStyle}>
              Add Another Course
            </button>

            <div style={{ display: "flex", gap: "1rem", marginBottom: "1.5rem" }}>
              <div style={{ flex: 1 }}>
                <input
                  type="text"
                  placeholder="Enter Year"
                  value={year}
                  onChange={(e) => setYear(e.target.value)}
                  style={inputStyle}
                />
              </div>
              <div style={{ flex: 1 }}>
                <select
                  id="session"
                  value={session}
                  onChange={(e) => setSession(e.target.value)}
                  style={inputStyle}
                >
                  <option value="">Select session</option>
                  <option value="FALL">Fall</option>
                  <option value="WINTERONLINE">Winter Online</option>
                  <option value="SPRING">Spring</option>
                  <option value="EARLYSUMMER">Early Summer</option>
                  <option value="LATESUMMER">Late Summer</option>
                </select>
              </div>
            </div>

            {error && <p style={{ color: "#990000", marginBottom: "1rem" }}>{error}</p>}

            <button onClick={generateSchedule} style={buttonStyle}>
              Generate
            </button>

            {scheduleLink && (
              <a
                href={scheduleLink}
                style={{
                  ...buttonStyle,
                  textDecoration: "none",
                  display: "inline-block",
                  marginTop: "1rem",
                }}
              >
                View Schedule
              </a>
            )}

            {conflicts && (
              <div style={{ marginTop: "2rem" }}>
                <h2 style={{ fontSize: "1.5rem", fontWeight: "bold", color: "#990000" }}>Conflicting Courses</h2>
                <ul style={{ listStyleType: "none", padding: 0 }}>
                  {conflicts.map((conflictGroup, index) => (
                    <li key={index} style={{ marginBottom: "0.5rem", color: "#333" }}>
                      {conflictGroup.map((course) => course.name).join(", ")}
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </div>
      );
    };

    const inputStyle = {
      width: "100%",
      padding: "0.8rem",
      marginTop: "0.5rem",
      borderRadius: "8px",
      border: "1px solid #ccc",
      fontSize: "1rem",
      color: "#333"
    };

    const buttonStyle = {
      backgroundColor: "#990000",
      color: "white",
      padding: "10px 20px",
      borderRadius: "8px",
      fontSize: "1.1rem",
      fontWeight: "bold",
      border: "none",
      cursor: "pointer",
      width: "100%",
      transition: "background-color 0.3s ease",
      marginTop: "1rem"
    };

    export default SchedulerPage;