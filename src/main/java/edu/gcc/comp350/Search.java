package edu.gcc.comp350;

import java.util.List;

public class Search {

    private String query;
    private Filter filter;
    private List<Course> searchResults;
    private List<Course> filteredResults;

    protected Search(String query, Filter filter) {

    }

    protected void search(String query) {

    }

    protected void applyFilter(Filter filter) {

    }

    protected void modifyFilter(Filter filter) {

    }

    protected void queryChange(String query) {

    }

    protected List<Course> getSearchResults() {
        return null;
    }

    protected List<Course> getFilteredResults() {
        return null;
    }

    protected Filter getFilter() {
        return null;
    }


}
