package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;

public class Search {

    private String query;
    private Filter filter;
    private List<Course> searchResults;
    private List<Course> filteredResults;

    protected Search(String query, Filter filter) {
        this.query = query;
        this.filter = filter;
    }

    protected void search(String query) {

    }

    protected void applyFilter(Filter filter) {
       List<Course> temp = new ArrayList<>();


        for (Course course : temp) {

        }
    }

    protected void modifyFilter(Filter filter) {

    }

    protected void queryChange(String query) {
        this.query = query;
        search(query);
    }

    protected List<Course> getSearchResults() {
        return searchResults;
    }

    protected List<Course> getFilteredResults() {
        return filteredResults;
    }

    protected Filter getFilter() {
        return filter;
    }


}
