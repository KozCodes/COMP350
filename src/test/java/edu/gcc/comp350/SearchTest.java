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
    RefactoredMain.Days testDay = RefactoredMain.Days.MWF;
    Time testStartTime = Time.valueOf("09:00:00");
    Time testEndTime = Time.valueOf("10:30:00");
    RefactoredMain.Session testSession = RefactoredMain.Session.FALL;
    List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
    String testDepartment = "Computer Science";

    // Create Filter instance
    Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
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
        RefactoredMain.Days testDay = RefactoredMain.Days.MWF;

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), RefactoredMain.Session.BLANK, tempcodes, "");

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
        Time testStartTime = Time.valueOf("09:00:00");

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(RefactoredMain.Days.BLANK, testStartTime, Time.valueOf("00:00:00"), RefactoredMain.Session.BLANK, tempcodes, "");

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
        RefactoredMain.Days testDay = RefactoredMain.Days.MWF;

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, Time.valueOf("00:00:00"), Time.valueOf("00:00:00"), RefactoredMain.Session.BLANK, tempcodes, "");

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
        Time testStartTime = Time.valueOf("09:00:00");

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(RefactoredMain.Days.BLANK, testStartTime, "00:00:00", "BLANK", tempcodes, "");

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
        String testDay = "MWF";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", "BLANK", tempcodes, "");

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithEndTimeFilter() throws Exception {
       onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        String testEndTime = "12:50:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", "00:00:00", testEndTime, "BLANK", tempcodes, "");

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
        String testSession = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", testSession, tempcodes, "");

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
        String testSession = "2023_Fall";
        String testSession2 = "2024_Spring";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", testSession, tempcodes, "");
        Filter filter2 = new Filter("BLANK", "00:00:00", "00:00:00", testSession2, tempcodes, "");

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
        String testDay = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", testDay, tempcodes, "");

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

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", "BLANK", testCourseCodes, "");

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

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", "BLANK", testCourseCodes, "");

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

        Filter filter = new Filter("BLANK", "00:00:00", "00:00:00", "BLANK", tempcodes, testDepartment);

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
        String testDay = "MWF";
        String testStartTime = "12:00:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, testStartTime, "00:00:00", "BLANK", tempcodes, "");

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
        String testDay = "MWF";
        String testStartTime = "09:00:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, testStartTime, "00:00:00", "BLANK", tempcodes, "");

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
        String testStartTime = "09:00:00";
        String testEndTime = "09:50:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", testStartTime, testEndTime, "BLANK", tempcodes, "");

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
        String testStartTime = "09:00:00";
        String testEndTime = "09:50:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", testStartTime, testEndTime, "BLANK", tempcodes, "");

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
        String testDay = "MWF";
        String testEndTime = "09:50:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", testEndTime, "BLANK", tempcodes, "");

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
        String testDay = "MWF";
        String testSession = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", testSession, tempcodes, "");

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
        String testDay = "MWF";
        String testSession = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", testSession, tempcodes, "");

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
        String testDay = "MWF";
        List<String> testCourseCodes = Arrays.asList("CHEM 111 A", "CHEM 113 A");

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", "BLANK", testCourseCodes, "");

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
        String testStartTime = "12:00:00";
        String testEndTime = "12:50:00";
        String testSession = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", testStartTime, testEndTime, testSession, tempcodes, "");

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
        String testDay = "MWF";
        String testStartTime = "12:00:00";
        String testEndTime = "12:50:00";
        String testSession = "2023_Fall";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, tempcodes, "");

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
        String testStartTime = "12:00:00";
        String testEndTime = "12:50:00";
        String testSession = "2023_Fall";
        List<String> testCourseCodes = Arrays.asList("CHEM 111 B");

        Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, "");

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
        RefactoredMain.Days testDay = RefactoredMain.Days.MWF;
        String testStartTime = "12:00:00";
        String testEndTime = "12:50:00";
        String testSession = "2023_Fall";
        List<String> testCourseCodes = Arrays.asList("CHEM 111 B");
        String testDepartment = "CHEM";

        Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

}

