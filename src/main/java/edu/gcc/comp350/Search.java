package edu.gcc.comp350;

import java.sql.Time;
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
        this.searchResults = new ArrayList<>();
        this.filteredResults = new ArrayList<>();
    }

    protected void search(String query) {
        //likely 4 things a user can search with: a name, a course code, a department,
        //or a prof's name. first, need to check what type the query is.

        //can add implementation later to check query for a description of a course, or
        //recommending courses based on a keyword that returns no results (ie spelling errors,
        //wrong data, etc.)

        //easiest - make a list of course codes based on DB data, then check if
        //query contains any course code

        //code generated with the assistance of Github Copilot for this section

            List<String> codes = new ArrayList<>();

            for (int i = 0; i < Main.courses.size(); i++) {
                if (!codes.contains(Main.courses.get(i).getCourseCode())) {
                    codes.add(Main.courses.get(i).getCourseCode());
                }
            }

        //check course codes first - will check for matches between the entire code,
        //the department, and the number. Will add if theres a match.
        // not in order, will order down below

        List<String> searchMatches = new ArrayList<>();

        for (String code : codes) {
            if (code.contains(query) || code.equals(query)) {
                if (!searchMatches.contains(code)) {
                        searchMatches.add(code);
                }

            }
        }

        for (int i = 0; i < codes.size(); i++) {
            System.out.println(codes.get(i));
        }

        for (int i = 0; i < Main.courses.size(); i++) {
            if (!searchResults.contains(Main.courses.get(i)) && searchMatches.contains(Main.courses.get(i).getCourseCode())) {
                searchResults.add(Main.courses.get(i));
            }
        }

        //keywords: get the query, and if it doesnt match any sort of course code,
        //try matching it to a name

        if (searchResults.isEmpty()) {
            
        }

        //print out search results
        if (filter != null) {
            applyFilter(filter);
            for (int i = 0; i < filteredResults.size(); i++) {
                System.out.println(filteredResults.get(i));
            }
        } else {
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println(searchResults.get(i).getCourseCode());
            }
        }
    }

    protected void applyFilter(Filter filter) {

        List<Course> toBeFiltered = new ArrayList<>();

        for (int i = 0; i < searchResults.size(); i++) {
            toBeFiltered.add(searchResults.get(i));
        }

        if (!filter.getCourse().equals("BLANK")) {
            for (Course course : toBeFiltered) {
                if (course.getCourseDays() != filter.getCourse()) {
                    toBeFiltered.remove(course);
                }
            }
        }

        if (!filter.getStartTime().equals("00:00:00")) {
            for (Course course : toBeFiltered) {
                if (course.getStartTime() != filter.getStartTime()) {
                    toBeFiltered.remove(course);
                }
            }
        }

        if (!filter.getEndTime().equals("00:00:00")) {
            for (Course course : toBeFiltered) {
                if (course.getEndTime() != filter.getEndTime()) {
                    toBeFiltered.remove(course);
                }
            }
        }

        if (!filter.getCourseSession().equals("BLANK")) {
            for(Course course : toBeFiltered) {
                if (course.getSession() != filter.getCourseSession()) {
                    toBeFiltered.remove(course);
                }
            }
        }

        if (!filter.getCourseCodes().isEmpty()) {
            for (String codes : filter.getCourseCodes()) {
                for(Course course : toBeFiltered) {
                    if (!course.getCourseCode().equals(codes)) {
                        toBeFiltered.remove(course);
                    }
                }
            }
        }

        if (!filter.getDepartment().equals("")) {
                for(Course course : toBeFiltered) {
                    if (!course.getCourseDept().equals(filter.getDepartment())) {
                        toBeFiltered.remove(course);
                    }
                }
        }

        if (!toBeFiltered.isEmpty()) {
            for (int i = 0; i < toBeFiltered.size(); i++) {
                filteredResults.add(toBeFiltered.get(i));
            }
        } else {
            System.out.println("I'm sorry, we're unable to find anything related to your search. Try modifying your filters or query.");
        }

    }

    protected void modifyFilter(Filter filter) {
        this.filter = filter;
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

    //added method, used for bug testing mostly
    protected String getQuery() {
        return query;
    }

}
