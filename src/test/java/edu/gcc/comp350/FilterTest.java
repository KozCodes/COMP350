package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Tests generated with ChatGPT and Github Copilot; edited to work within filter file
class FilterTest {

    // Subclass to access the protected constructor
    private static class TestFilter extends Filter {
        public TestFilter(List<RefactoredMain.Days> course, List<Time> startTime, List<Time> endTime,
                          RefactoredMain.Session courseSession, List<String> courseCodes,
                          String department, int year) {
            super(course, startTime, endTime, courseSession, courseCodes, department, year);
        }
    }

    @Test
    void testFilterConstructor() {
        // Sample inputs
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("10:30:00"));

        // Create Filter instance
        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        // Assertions
        assertEquals(days, filter.getCourse());
        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
        assertEquals(testSession, filter.getCourseSession());
        assertEquals(testCourseCodes, filter.getCourseCodes());
        assertEquals(testDepartment, filter.getDepartment());
    }

    @Test
    void testBoundaryTimes() {
        RefactoredMain.Session testSession = RefactoredMain.Session.WINTER;
        List<String> testCourseCodes = Arrays.asList("ENG201", "ENG202");
        String testDepartment = "English";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.T);
        days.add(RefactoredMain.Days.R);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("08:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("21:00:00"));

        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    //start and end cannot be the same time if starting from beginning of accepted times
    @Test
    void testSameStartAndEndTime() {
        RefactoredMain.Session testSession = RefactoredMain.Session.SPRING;
        List<String> testCourseCodes = Arrays.asList("BIO101");
        String testDepartment = "Biology";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("08:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("08:00:00"));

        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 0000);

        assertEquals(testStartTime, filter.getStartTime());
        assertNotEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void testSetters() {
        RefactoredMain.Session testSession = RefactoredMain.Session.EARLYSUMMER;
        List<String> testCourseCodes = Arrays.asList("HIS101", "HIS102");
        String testDepartment = "History";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.T);
        days.add(RefactoredMain.Days.R);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("10:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("11:00:00"));

        List<RefactoredMain.Days> dayse = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTimee = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTimee = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        TestFilter filter = new TestFilter(dayse, testStartTimee, testEndTimee, RefactoredMain.Session.BLANK, Arrays.asList(), "", 0000);

        filter.setCourse(days);
        filter.setStartTime(testStartTime);
        filter.setEndTime(testEndTime);
        filter.setCourseSession(testSession);
        filter.setCourseCodes(testCourseCodes);
        filter.setDepartment(testDepartment);

        assertEquals(days, filter.getCourse());
        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
        assertEquals(testSession, filter.getCourseSession());
        assertEquals(testCourseCodes, filter.getCourseCodes());
        assertEquals(testDepartment, filter.getDepartment());
    }

  @Test
    void testFilterEquals() {
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

      List<RefactoredMain.Days> days = new ArrayList<>();

      days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

      List<Time> testStartTime = new ArrayList<>();
      testStartTime.add(Time.valueOf("09:00:00"));

      List<Time> testEndTime = new ArrayList<>();
      testEndTime.add(Time.valueOf("10:30:00"));

        TestFilter filter1 = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);
        TestFilter filter2 = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        assertEquals(filter1.getCourse(), filter2.getCourse());
      assertEquals(filter1.getStartTime(), filter2.getStartTime());
        assertEquals(filter1.getEndTime(), filter2.getEndTime());
        assertEquals(filter1.getCourseSession(), filter2.getCourseSession());
        assertEquals(filter1.getCourseCodes(), filter2.getCourseCodes());
        assertEquals(filter1.getDepartment(), filter2.getDepartment());
    }

    @Test
    void testFilterNotEquals() {
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<RefactoredMain.Days> testDayList = new ArrayList<>();
        testDayList.add(RefactoredMain.Days.T);
        testDayList.add(RefactoredMain.Days.R);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("10:30:00"));

        TestFilter filter1 = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);
        TestFilter filter2 = new TestFilter(testDayList, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        assertNotEquals(filter1.getCourse(), filter2.getCourse());
    }

    @Test
    void testFilterNotEqualsNull() {
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("10:30:00"));

        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        assertNotEquals(filter, null);
    }

    @Test
    void validDates() {
         RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("10:30:00"));

        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        assertEquals(testStartTime, filter.getStartTime());
        assertEquals(testEndTime, filter.getEndTime());
    }

    @Test
    void invalidDates() {
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
        String testDepartment = "Computer Science";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("07:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("22:30:00"));

        TestFilter filter = new TestFilter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        List<Time> testStartTime2 = new ArrayList<>();
        testStartTime2.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime2 = new ArrayList<>();
        testEndTime2.add(Time.valueOf("00:00:00"));

        assertEquals(testStartTime2, filter.getStartTime());
        assertEquals(testEndTime2, filter.getEndTime());
    }
}

