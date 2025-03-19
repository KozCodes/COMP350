package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//all tests generated with Github Copilot; edited to work within search file
//currently only search constructor test until database is implemented properly
public class SearchTest {
@Test
void testSearchConstructor() {
    // Sample inputs
    String testQuery = "Computer Science";
    String testDay = "MWF";
    String testStartTime = "09:00:00";
    String testEndTime = "10:30:00";
    String testSession = "2023_FALL";
    List<String> testCourseCodes = Arrays.asList("CS101", "CS102");
    String testDepartment = "Computer Science";

    // Create Filter instance
    Filter filter = new Filter(testDay, testStartTime, testEndTime, testSession, testCourseCodes, testDepartment);
    Search search = new Search(testQuery, filter);

    // Assertions
    assertEquals(testQuery, search.getQuery());
    assertEquals(filter, search.getFilter());
}

@Test
void testSimpleDepartmentSearchWithNoFilters() throws Exception {

    Main.onLoad();

    // Sample inputs
    String testQuery = "COMP";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());
    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
void testSimpleCourseCodeSearchWithNoFilters() throws Exception {

    Main.onLoad();

    // Sample inputs
    String testQuery = "COMP 141";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());

    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
void testSimpleCompleteCourseCodeSearchWithNoFilters() throws Exception {

    Main.onLoad();

    // Sample inputs
    String testQuery = "COMP 141 A";

    Search search = new Search(testQuery, null);

    search.search(search.getQuery());

    // Assertions
    assertNotEquals(0, search.getSearchResults().size());
}

@Test
    void testSimpleKeywordSearchWithNoFilters() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void testSimpleNameSearchWithNoFilters() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "GENERAL CHEMISTRY I";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getSearchResults().size());
    }

    @Test
    void testReactiontoNoResults() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "BBQ";

        Search search = new Search(testQuery, null);

        search.search(search.getQuery());

        // Assertions
        assertEquals(0, search.getSearchResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithDayFilter() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "COMP";
        String testDay = "MWF";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", "BLANK", tempcodes, "");

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithTimeFilter() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "COMP";
        String testStartTime = "09:00:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", testStartTime, "00:00:00", "BLANK", tempcodes, "");

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleDepartmentSearchWithDayAndTimeFilter() throws Exception {

        Main.onLoad();

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
    void testSimpleKeywordSearchWithDayFilter() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        String testDay = "MWF";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter(testDay, "00:00:00", "00:00:00", "BLANK", tempcodes, "");

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }

    @Test
    void testSimpleKeywordSearchWithTimeFilter() throws Exception {

        Main.onLoad();

        // Sample inputs
        String testQuery = "CHEMISTRY";
        String testStartTime = "09:00:00";

        List<String> tempcodes = new ArrayList<>();

        Filter filter = new Filter("BLANK", testStartTime, "00:00:00", "BLANK", tempcodes, "");

        Search search = new Search(testQuery, filter);

        search.search(search.getQuery());

        // Assertions
        assertNotEquals(0, search.getFilteredResults().size());
    }
}

