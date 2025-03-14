package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

//Tests generated with ChatGPT; edited to work within filter file
class FilterTest {

    // Subclass to access the protected constructor
    private static class TestFilter extends Filter {
        public TestFilter(Main.Days course, Time startTime, Time endTime,
                          Main.Session courseSession, List<String> courseCodes,
                          String department) {
            super(course, startTime, endTime, courseSession, courseCodes, department);
        }
    }

    @Test
    void testFilterConstructor() {
        // Sample inputs
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
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
    void testNullDepartment() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("13:00:00");
        Time testEndTime = Time.valueOf("14:30:00");
        Main.Session testSession = Main.Session.EARLYSUMMER;
        List<String> testCourseCodes = Arrays.asList("PHY101");
        String testDepartment = null;

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNull(filter.getDepartment());
    }

    @Test
    void testBoundaryTimes() {
        Main.Days testDay = Main.Days.TR;
        Time testStartTime = Time.valueOf("00:00:00");
        Time testEndTime = Time.valueOf("23:59:59");
        Main.Session testSession = Main.Session.WINTER;
        List<String> testCourseCodes = Arrays.asList("ENG201", "ENG202");
        String testDepartment = "English";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void testNullCourseCodes() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("15:00:00");
        Time testEndTime = Time.valueOf("16:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = null;
        String testDepartment = "History";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNull(filter.getCourseCodes());
    }

    @Test
    void testSameStartAndEndTime() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("08:00:00");
        Time testEndTime = Time.valueOf("08:00:00");
        Main.Session testSession = Main.Session.SPRING;
        List<String> testCourseCodes = Arrays.asList("BIO101");
        String testDepartment = "Biology";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }
}

