package edu.gcc.comp350;

import org.junit.jupiter.api.Test;

import static edu.gcc.comp350.RESTController.onLoad;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//all tests generated with Github Copilot; edited to work within search file
public class SearchTest {

    //Search Constructor Tests
@Test
void testSearchConstructor() {
    // Sample inputs
    String testQuery = "Computer Science";
    List<RefactoredMain.Days> testDay = new ArrayList<>();
    testDay.add(RefactoredMain.Days.MWF);
    List<Time> testStartTime = new ArrayList<>();
    testStartTime.add(Time.valueOf("09:00:00"));
    List<Time> testEndTime = new ArrayList<>();
    testEndTime.add(Time.valueOf("10:30:00"));
    RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
    List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
    String testDepartment = "Computer Science";
    int year = 2023;

    // Create Filter instance
    Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, year);
    Search search = new Search(testQuery, filter);

    // Assertions
    assertEquals(testQuery, search.getQuery());
    assertEquals(filter, search.getFilter());
}

//Simple Searches Without Filters, all types of searches
@Test
void testSimpleDepartmentSearchWithNoFilters() throws Exception {

    onLoad();

    // Sample inputs
    String testQuery = "COMP";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());

    System.out.println(search.getSearchResults());
    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
void testSimpleCourseCodeSearchWithNoFilters() throws Exception {

   onLoad();

    // Sample inputs
    String testQuery = "COMP 141";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());

    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
void testSimpleCompleteCourseCodeSearchWithNoFilters() throws Exception {

    onLoad();

    // Sample inputs
    String testQuery = "COMP 141 A";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());

    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
    void testSimpleKeywordSearchWithNoFilters() throws Exception {

        onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void testSimpleNameSearchWithNoFilters() throws Exception {

 onLoad();

        // Sample inputs
        String testQuery = "GENERAL CHEMISTRY I";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void lowercaseQuery() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "chemistry";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void MixedQuery() throws Exception {
     onLoad();

        // Sample inputs
        String testQuery = "cHeMiStRy";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    //Reactions to No Results, invalid searches
    @Test
    void testReactiontoNoResults() throws Exception {

  onLoad();

        // Sample inputs
        String testQuery = "BBQ";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertEquals(0, search.getSearchResults().size());
    }

    //Simple Searches with One Type of Filter Activated
    @Test
    void testSimpleDepartmentSearchWithDayFilter() throws Exception {

  onLoad();

        // Sample inputs
        String testQuery = "COMP";
        List<RefactoredMain.Days> testDay = new ArrayList<>();

        testDay.add(RefactoredMain.Days.MWF);

        List<String> tempcodes = new ArrayList<>();
        List<Time> start = new ArrayList<>();
        start.add(Time.valueOf("00:00:00"));

        List<Time> end = new ArrayList<>();
        end.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(testDay, start, end, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithTimeFilter() throws Exception {

    onLoad();

        // Sample inputs
        String testQuery = "COMP";
        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<String> tempcodes = new ArrayList<>();

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleKeywordSearchWithDayFilter() throws Exception {

    onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        List<RefactoredMain.Days> testDay = new ArrayList<>();

        testDay.add(RefactoredMain.Days.M);
        testDay.add(RefactoredMain.Days.W);
        testDay.add(RefactoredMain.Days.F);

        List<String> tempcodes = new ArrayList<>();

        List<Time> start = new ArrayList<>();
        start.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(testDay, start, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleKeywordSearchWithTimeFilter() throws Exception {

     onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleNameSearchWithDayFilter() throws Exception {

       onLoad();

        // Sample inputs
        String testQuery = "GENERAL CHEMISTRY I";
        List<RefactoredMain.Days> testDay = new ArrayList<>();

        testDay.add(RefactoredMain.Days.M);
        testDay.add(RefactoredMain.Days.W);
        testDay.add(RefactoredMain.Days.F);

        List<String> tempcodes = new ArrayList<>();

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(testDay, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithEndTimeFilter() throws Exception {
       onLoad();
        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        // Sample inputs
        String testQuery = "CHEMISTRY";
        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("12:50:00"));

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithSessionFilter() throws Exception {
       onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.FALL, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void ClassesInSpringandFall() throws Exception {
     onLoad();

        // Sample inputs
        String testQuery = "HUMA";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        RefactoredMain.Session testSession2 = RefactoredMain.Session.SPRING;

        List<String> tempcodes = new ArrayList<>();


        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, tempcodes, "", 2023);
        Filter filter2 = new Filter(days, testStartTime, testEndTime, testSession2, tempcodes, "", 2023);

        Search search = new Search(testQuery, filter);
        Search search2 = new Search(testQuery, filter2);

        search.search(search.getQuery());
        search2.search(search2.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
        assertNotEquals(0, search2.getFilteredResults().size());
    }

    @Test
    void testSimpleCourseCodeSearchWithSessionFilter() throws Exception {
    onLoad();

        // Sample inputs
        String testQuery = "COMP 141";
        RefactoredMain.Session testDay = RefactoredMain.Session.FALL;

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testDay, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleCourseCodeSearchWithCourseCodeFilter() throws Exception {
   onLoad();

        // Sample inputs
        String testQuery = "COMP";
        List<String> testCourseCodes = Arrays.asList("COMP 141 A", "COMP 314 A");

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));


        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, testCourseCodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithCourseCodeFilter() throws Exception {
    onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        List<String> testCourseCodes = Arrays.asList("CHEM 111 A", "CHEM 113 A");

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, testCourseCodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleCourseCodeSearchWithDepartmentFilter() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "111";
        String testDepartment = "CHEM";

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, testDepartment, 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

//Tests with Simple Searches with Multiple Filters Active
    @Test
    void testSimpleKeywordSearchWithDayAndTimeFilter() throws Exception {

      onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("12:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithDayAndTimeFilter() throws Exception {

        onLoad();

        // Sample inputs
        String testQuery = "COMP";
        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleCourseCodeSearchwithBothTimesFilters() throws Exception {

        onLoad();

        // Sample inputs
        String testQuery = "COMP";
        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("09:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleKeywordSearchwithBothTimesFilters() throws Exception {

       onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("09:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("09:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchwithDayandTimesFilters() throws Exception {

       onLoad();

        // Sample inputs
        String testQuery = "CHEM";

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("09:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, tempcodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchwithDayandSessionFilters() throws Exception {

     onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, tempcodes, "", 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleKeywordSearchwithDayandSessionFilters() throws Exception {

         onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, tempcodes, "", 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchwithDayandCourseCodeFilters() throws Exception {

    onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        List<String> testCourseCodes = Arrays.asList("CHEM 111 A", "CHEM 113 A");

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("00:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("00:00:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, RefactoredMain.Session.BLANK, testCourseCodes, "", 0000);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void ComplexSearchwith3Filters() throws Exception {
     onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.BLANK);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("12:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("12:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, tempcodes, "", 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void ComplexSearchwith4Filters() throws Exception {
      onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;

        List<String> tempcodes = new ArrayList<>();

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("12:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("12:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, tempcodes, "", 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void ComplexSearchwith5Filters() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        String testDay = "MWF";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CHEM 111 B");

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("12:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("12:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, testCourseCodes, "", 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void AllFilters() throws Exception{
        onLoad();

        // Sample inputs
        String testQuery = "CHEM";
        RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
        List<String> testCourseCodes = Arrays.asList("CHEM 111 B");
        String testDepartment = "CHEM";

        List<RefactoredMain.Days> days = new ArrayList<>();

        days.add(RefactoredMain.Days.M);
        days.add(RefactoredMain.Days.W);
        days.add(RefactoredMain.Days.F);

        List<Time> testStartTime = new ArrayList<>();
        testStartTime.add(Time.valueOf("12:00:00"));

        List<Time> testEndTime = new ArrayList<>();
        testEndTime.add(Time.valueOf("12:50:00"));

        Filter filter = new Filter(days, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment, 2023);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }



    @Test
    void testSpellingMistakes_Missing() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "CHEMSTRY";

        Filter filter = new Filter();

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void testSpellingMistakes_Extra() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRYX";

        Filter filter = new Filter();

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void testSpellingMistakes_Transposed() throws Exception {
        onLoad();

        // Sample inputs
        String testQuery = "CHEMISTYR";

        Filter filter = new Filter();

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void TestSpellingMistakes_
}

