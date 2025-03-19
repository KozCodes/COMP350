package edu.gcc.comp350;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;
    private Schedule schedule1;
    private Schedule schedule2;

    @BeforeEach
    void setUp() {
        List<String> minors = new ArrayList<>();
        minors.add("Mathematics");
        minors.add("Computer Science");

  //      student = new Student(1, "Jonah", "Computer Science", minors);
    }

    @Test
    void testGetId() {
        assertEquals(1, student.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Jonah", student.getName());
    }

    @Test
    void testSetName() {
        student.setName("John");
        assertEquals("John", student.getName());
    }

    @Test
    void testGetMajor() {
        assertEquals("Computer Science", student.getMajor());
    }

    @Test
    void testGetMinors() {
        List<String> minors = student.getMinors();
        assertEquals(2, minors.size());
        assertTrue(minors.contains("Mathematics"));
        assertTrue(minors.contains("Computer Science"));
    }

//    @Test
//    void testAddSchedule() {
//        student.addSchedule(schedule1);
//        assertNotNull(student.getSchedules());
//        assertEquals(1, student.getSchedules().size());
//        assertTrue(student.getSchedules().contains(schedule1));
//    }
//
//    @Test
//    void testDeleteSchedule() {
//        student.addSchedule(schedule1);
//        student.addSchedule(schedule2);
//        student.deleteSchedule(schedule1);
//
//        assertEquals(1, student.getSchedules().size());
//        assertFalse(student.getSchedules().contains(schedule1));
//    }
//
//    @Test
//    void testGetSchedule() {
//        student.addSchedule(schedule1);
//        student.addSchedule(schedule2);
//
//        Schedule retrievedSchedule = student.getSchedule(101);
//        assertNotNull(retrievedSchedule);
//        assertEquals(101, retrievedSchedule.getId());
//    }
//
//    @Test
//    void testGetScheduleNotFound() {
//        student.addSchedule(schedule1);
//        assertNull(student.getSchedule(999)); // ID that doesn't exist
//    }
}
