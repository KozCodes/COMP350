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

    @Test
    void testSetters() {
        Main.Days testDay = Main.Days.TR;
        Time testStartTime = Time.valueOf("10:00:00");
        Time testEndTime = Time.valueOf("11:00:00");
        Main.Session testSession = Main.Session.EARLYSUMMER;
        List<String> testCourseCodes = Arrays.asList("HIS101", "HIS102");
        String testDepartment = "History";

        TestFilter filter = new TestFilter(Main.Days.BLANK, Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), Main.Session.BLANK, Arrays.asList(), "");

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
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter1 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
        TestFilter filter2 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(filter1, filter2);
    }

    @Test
    void testFilterNotEquals() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter1 = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
        TestFilter filter2 = new TestFilter(Main.Days.TR, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter1, filter2);
    }

    @Test
    void testFilterNotEqualsDifferentType() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter, testDay);
    }

    @Test
    void testFilterNotEqualsNull() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertNotEquals(filter, null);
    }

    @Test
    void validDates() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("09:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void invalidDates() {
        Main.Days testDay = Main.Days.MWF;
        Time testStartTime = Time.valueOf("07:00:00");
        Time testEndTime = Time.valueOf("10:30:00");
        Main.Session testSession = Main.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        TestFilter filter = new TestFilter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        assertEquals(Time.valueOf("00:00:00"), filter.getStartTime());
        assertEquals(Time.valueOf("00:00:00"), filter.getEndTime());
    }
}

