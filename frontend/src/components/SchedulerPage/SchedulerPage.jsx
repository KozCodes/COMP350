import React, { useState } from "react";
                      import axios from "axios";
                      import { DndContext, closestCenter } from "@dnd-kit/core";
                      import { arrayMove, SortableContext, useSortable, verticalListSortingStrategy } from "@dnd-kit/sortable";
                      import { CSS } from "@dnd-kit/utilities";

                      const SortableItem = ({ id, name }) => {
                        const { attributes, listeners, setNodeRef, transform, transition } = useSortable({ id });

                        const style = {
                          transform: CSS.Transform.toString(transform),
                          transition,
                          padding: "8px",
                          margin: "4px 0",
                          backgroundColor: "#f8f9fa",
                          border: "1px solid #ced4da",
                          borderRadius: "4px",
                        };

                        return (
                          <div ref={setNodeRef} style={style} {...attributes} {...listeners}>
                            {name || "Empty Course"}
                          </div>
                        );
                      };

                      const SchedulerPage = () => {
                        const [courses, setCourses] = useState([{ id: 1, name: "" }]);
                        const [error, setError] = useState("");
                        const [schedule, setSchedule] = useState(null);
                        const [conflicts, setConflicts] = useState(null);

                        const handleInputChange = (index, value) => {
                          const updatedCourses = [...courses];
                          updatedCourses[index].name = value;
                          setCourses(updatedCourses);
                        };

                        const addCourseField = () => {
                          setCourses([...courses, { id: courses.length + 1, name: "" }]);
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
                              courses: courses.map((course) => course.name),
                            });

                            if (Array.isArray(response.data) && response.data.length > 0) {
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
                          <div className="container mt-4">
                            <h1 className="text-center mb-4">Scheduler Page</h1>
                            <p>Enter potential courses and list them in order of preference:</p>

                            {courses.map((course, index) => (
                              <div key={course.id} className="mb-3">
                                <input
                                  type="text"
                                  className="form-control"
                                  placeholder="Enter potential Course"
                                  value={course.name}
                                  onChange={(e) => handleInputChange(index, e.target.value)}
                                />
                              </div>
                            ))}

                            <button className="btn btn-primary mb-3" onClick={addCourseField}>
                              Add Another Course
                            </button>

                            {error && <p className="text-danger">{error}</p>}

                            <DndContext collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
                              <SortableContext items={courses} strategy={verticalListSortingStrategy}>
                                {courses.map((course) => (
                                  <SortableItem key={course.id} id={course.id} name={course.name} />
                                ))}
                              </SortableContext>
                            </DndContext>

                            <button className="btn btn-success" onClick={generateSchedule}>
                              Generate
                            </button>

                            {schedule && (
                              <div className="mt-4">
                                <h2>Generated Schedule</h2>
                                <ul className="list-group">
                                  {schedule.map((item) => (
                                    <li key={item.id} className="list-group-item">
                                      {JSON.stringify(item)}
                                    </li>
                                  ))}
                                </ul>
                              </div>
                            )}

                            {conflicts && (
                              <div className="mt-4">
                                <h2>Conflicting Courses</h2>
                                <ul className="list-group">
                                  {conflicts.map((conflictGroup, index) => (
                                    <li key={index} className="list-group-item">
                                      {conflictGroup.map((course) => course.name).join(", ")}
                                    </li>
                                  ))}
                                </ul>
                              </div>
                            )}
                          </div>
                        );
                      };

                      export default SchedulerPage;