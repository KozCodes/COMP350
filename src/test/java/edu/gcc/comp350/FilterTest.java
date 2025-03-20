package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

//Tests generated with ChatGPT and Github Copilot; edited to work within filter file
class FilterTest {

    // Subclass to access the protected constructor
    private static class TestFilter extends Filter {
        public TestFilter(String course, String startTime, String endTime,
                          String courseSession, List<String> courseCodes,
                          String department) {
            super(course, startTime, endTime, courseSession, courseCodes, department);
        }
    }

    @Test
    void testFilterConstructor() {
        // Sample inputs
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        // Create Filter instance
        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        // Assertions
        assertEquals(testDay, filter.getCourse());
        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
        assertEquals(testSession, filter.getCourseSession());
        assertEquals(testCourseCodes, filter.getCourseCodes());
        assertEquals(testDepartment, filter.getDepartment());
    }

    @Test
    void testBoundaryTimes() {
        String testDay = "TR";
        String testStartTime = "08:00:00";
        String testEndTime = "21:00:00";
        String testSession = "2023_WINTER";
        List<String> testCourseCodes = Arrays.asList("ENG201", "ENG202");
        String testDepartment = "English";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    //start and end cannot be the same time if starting from beginning of accepted times
    @Test
    void testSameStartAndEndTime() {
        String testDay = "MWF";
        String testStartTime = "08:00:00";
        String testEndTime = "08:00:00";
        String testSession = "2024_SPRING";
        List<String> testCourseCodes = Arrays.asList("BIO101");
        String testDepartment = "Biology";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertNotEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void testSetters() {
        String testDay = "TR";
        String testStartTime = "10:00:00";
        String testEndTime = "11:00:00";
        String testSession = "2024_EARLYSUMMER";
        List<String> testCourseCodes = Arrays.asList("HIS101", "HIS102");
        String testDepartment = "History";

        TestFilter filter = new TestFilter("BLANK", "00:00:00", "00:00:00", "BLANK", Arrays.asList(), "");

        filter.setCourse(testDay);
        filter.setStartTime(testStartTime);
        filter.setEndTime(testEndTime);
        filter.setCourseSession(testSession);
        filter.setCourseCodes(testCourseCodes);
        filter.setDepartment(testDepartment);

        assertEquals(testDay, filter.getCourse());
        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
        assertEquals(testSession, filter.getCourseSession());
        assertEquals(testCourseCodes, filter.getCourseCodes());
        assertEquals(testDepartment, filter.getDepartment());
    }

  @Test
    void testFilterEquals() {
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter1 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
        TestFilter filter2 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(filter1.getCourse(), filter2.getCourse());
      assertEquals(filter1.getStartTime(), filter2.getStartTime());
        assertEquals(filter1.getEndTime(), filter2.getEndTime());
        assertEquals(filter1.getCourseSession(), filter2.getCourseSession());
        assertEquals(filter1.getCourseCodes(), filter2.getCourseCodes());
        assertEquals(filter1.getDepartment(), filter2.getDepartment());
    }

    @Test
    void testFilterNotEquals() {
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter1 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
        TestFilter filter2 = new TestFilter("TR", testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter1.getCourse(), filter2.getCourse());
    }

    @Test
    void testFilterNotEqualsDifferentType() {
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter, testDay);
    }

    @Test
    void testFilterNotEqualsNull() {
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter, null);
    }

    @Test
    void validDates() {
        String testDay = "MWF";
        String testStartTime = "09:00:00";
        String testEndTime = "10:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void invalidDates() {
        String testDay = "MWF";
        String testStartTime = "07:00:00";
        String testEndTime = "22:30:00";
        String testSession = "2023_FALL";
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals("00:00:00", filter.getStartTime());
        assertEquals("00:00:00", filter.getEndTime());
    }
}

