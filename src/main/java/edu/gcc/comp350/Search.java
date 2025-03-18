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

            //sort by best matches: find code with equal first
            for (String code : codes) {
                if (query.contains(code)) {
                    for (int i = 0; i < Main.courses.size(); i++) {
                        if (Main.courses.get(i).getCourseCode().equals(code)) {
                            searchResults.add(Main.courses.get(i));
                        }
                    }
                }
            }

            //then, run through again and find courses containing a snippet of the code,
            //first with department, then with number (department could be incorrect)
        List<String> deptOnly = new ArrayList<>();

            for (String code: codes) {
                String temp = "";
                for (int i = 0; i < code.length(); i++) {
                    if (Character.isLetter(code.charAt(i))) {
                        temp += code.charAt(i);
                    }
                }

                deptOnly.add(temp);
            }

            for(String code: deptOnly) {
                if (query.contains(code)) {
                    for (int i = 0; i < Main.courses.size(); i++) {
                        if (Main.courses.get(i).getCourseCode().contains(code) && !searchResults.contains(Main.courses.get(i))) {
                            searchResults.add(Main.courses.get(i));
                        }
                    }
                }
            }

            List<String> numOnly = new ArrayList<>();
            for (String code: codes) {
                String temp = "";
                for (int i = 0; i < code.length(); i++) {
                    if (Character.isDigit(code.charAt(i))) {
                        temp += code.charAt(i);
                    }
                }

                numOnly.add(temp);
            }

            for(String code: numOnly) {
                if (query.contains(code)) {
                    for (int i = 0; i < Main.courses.size(); i++) {
                        if (Main.courses.get(i).getCourseCode().contains(code) && !searchResults.contains(Main.courses.get(i))) {
                            searchResults.add(Main.courses.get(i));
                        }
                    }
                }
            }

        //next easiest - make a list of departments based on DB data, then check query for match
        if (!searchResults.isEmpty()) {
            List<String> dept = new ArrayList<>();

            for (int i = 0; i < Main.courses.size(); i++) {
                if (!dept.contains(Main.courses.get(i).getCourseDept())) {
                    dept.add(Main.courses.get(i).getCourseDept());
                }
            }

            for (int i = 0; i < dept.size(); i++) {
                if (query.contains(dept.get(i))) {
                    for (int j = 0; j < Main.courses.size(); j++) {
                        if (Main.courses.get(j).getCourseDept().equals(dept.get(i)) && !searchResults.contains(Main.courses.get(j))) {
                            searchResults.add(Main.courses.get(j));
                        }
                    }
                }
            }

            if (!searchResults.isEmpty()) {
                //hardest - check query for a name, then check if it matches any professor names in the DB
                List<String> profs = new ArrayList<>();

                for (int i = 0; i < Main.courses.size(); i++) {
                    if (!profs.contains(Main.courses.get(i).getProfessor().getName())) {
                        profs.add(Main.courses.get(i).getProfessor().getName());
                    }
                }

                for(String professors : profs) {
                    if (query.contains(professors)) {
                        for (int i = 0; i < Main.courses.size(); i++) {
                            if (Main.courses.get(i).getCourseCode().contains(professors) && !searchResults.contains(Main.courses.get(i))) {
                                searchResults.add(Main.courses.get(i));
                            }
                        }
                    }
                }
            }
        }

        //TODO: harderer - check query for a snippet of a course name


        //print out search results
        if (filter != null) {
            applyFilter(filter);
            for (int i = 0; i < filteredResults.size(); i++) {
                System.out.println(filteredResults.get(i));
            }
        } else {
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println(searchResults.get(i));
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
