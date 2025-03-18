package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;
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
void testSearchWithNoFilters() {
    // Sample inputs
    String testQuery = "Computer Science";

    // Create Filter instance
    Filter filter = new Filter();
    Search search = new Search(testQuery, filter);

    search.search(search.getQuery());

    // Assertions
    assertNotNull(search.getSearchResults());
}
}

