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

        query = query.toUpperCase();

            List<String> codes = new ArrayList<>();

            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                if (!codes.contains(RefactoredMain.courses.get(i).getCourseCode())) {
                    codes.add(RefactoredMain.courses.get(i).getCourseCode());
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

        for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                if (searchMatches.contains(RefactoredMain.courses.get(i).getCourseCode()) && !searchResults.contains(RefactoredMain.courses.get(i))) {
                    searchResults.add(RefactoredMain.courses.get(i));
                }
        }

        //keywords: get the query, and if it doesnt match any sort of course code,
        //try matching it to a name

        if (searchResults.isEmpty()) {


            List<String> names = new ArrayList<>();

            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                if (!names.contains(RefactoredMain.courses.get(i).getCourseTitle())) {
                    names.add(RefactoredMain.courses.get(i).getCourseTitle());
                }
            }

            for (String name : names) {
                if (name.contains(query) || name.equals(query)) {
                    searchMatches.add(name);
                }
            }

            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                    if (searchMatches.contains(RefactoredMain.courses.get(i).getCourseTitle()) && !searchResults.contains(RefactoredMain.courses.get(i))) {
                        searchResults.add(RefactoredMain.courses.get(i));
                    }
            }
        }

        //print out search results
        if (filter != null) {
            applyFilter(filter);
        } else {
            if (searchResults.isEmpty()) {
                System.out.println("We're sorry, we were unable to find a course related to your search. Please try again.");
            }
        }
    }

    protected void applyFilter(Filter filter) {
        //filter bot - first checks if a part of a filter is a default value, in which case it will continue on to the next piece of a filter
        //until no more of it can be checked. After a filter piece is checked and it is not a default value, it will check all the other
        //values in the filter. if all other values are default, add the current course to be checked into the filtered list. if theres
        //more that are not default values, it will wait to add a course until it can be determined that the course sufficiently meets
        //all requirements.

            for (Course course : searchResults) {
                String condensedDays = course.getCourseDays().replaceAll(", ", "");
                String condensedTimes = course.getStartTime().split(",")[0];
                String condensedTimes2 = course.getEndTime().split(",")[0];

                if (condensedDays.equals(filter.getCourse()) && !filter.getCourse().equals("BLANK")) {
                    if (filter.getCourseSession().equals("BLANK") && filter.getStartTime().equals("00:00:00") && filter.getEndTime().equals("00:00:00") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                        filteredResults.add(course);
                    } else {
                      if (condensedTimes.equals(filter.getStartTime()) && !filter.getStartTime().equals("00:00:00")) {
                          if (filter.getEndTime().equals("00:00:00") && filter.getCourseSession().equals("BLANK") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                              filteredResults.add(course);
                          } else {
                                if (condensedTimes2.equals(filter.getEndTime()) && !filter.getEndTime().equals("00:00:00")) {
                                    if (filter.getCourseSession().equals("BLANK") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                        filteredResults.add(course);
                                    } else {
                                        if (course.getSession().equals(filter.getCourseSession()) && !filter.getCourseSession().equals("BLANK")) {
                                            if (filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                                    filteredResults.add(course);
                                            } else {
                                                if (filter.getCourseCodes().contains(course.getCourseCode()) && !filter.getCourseCodes().isEmpty()) {
                                                    if (filter.getDepartment().equals("")){
                                                        filteredResults.add(course);
                                                    } else {
                                                        if (course.getCourseDept().equals(filter.getDepartment()) && !filter.getDepartment().equals("")) {
                                                            filteredResults.add(course);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                          }
                      }
                    }
                } else {
                    if (condensedTimes.equals(filter.getStartTime()) && !filter.getStartTime().equals("00:00:00")) {
                        if (filter.getEndTime().equals("00:00:00") && filter.getCourseSession().equals("BLANK") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                            filteredResults.add(course);
                        } else {
                            if (condensedTimes2.equals(filter.getEndTime()) && !filter.getEndTime().equals("00:00:00")) {
                                if (filter.getCourseSession().equals("BLANK") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                    filteredResults.add(course);
                                } else {
                                    if (course.getSession().equals(filter.getCourseSession()) && !filter.getCourseSession().equals("BLANK")) {
                                        if (filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                            filteredResults.add(course);
                                        } else {
                                            if (filter.getCourseCodes().contains(course.getCourseCode()) && !filter.getCourseCodes().isEmpty()) {
                                                if (filter.getDepartment().equals("")){
                                                    filteredResults.add(course);
                                                } else {
                                                    if (course.getCourseDept().equals(filter.getDepartment()) && !filter.getDepartment().equals("")) {
                                                        filteredResults.add(course);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (condensedTimes2.equals(filter.getEndTime()) && !filter.getEndTime().equals("00:00:00")) {
                            if (filter.getCourseSession().equals("BLANK") && filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                filteredResults.add(course);
                            } else {
                                if (course.getSession().equals(filter.getCourseSession()) && !filter.getCourseSession().equals("BLANK")) {
                                    if (filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                        filteredResults.add(course);
                                    } else {
                                        if (filter.getCourseCodes().contains(course.getCourseCode()) && !filter.getCourseCodes().isEmpty()) {
                                            if (filter.getDepartment().equals("")){
                                                filteredResults.add(course);
                                            } else {
                                                if (course.getCourseDept().equals(filter.getDepartment())) {
                                                    filteredResults.add(course);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (course.getSession().equals(filter.getCourseSession()) && !filter.getCourseSession().equals("BLANK")) {
                                if (filter.getCourseCodes().isEmpty() && filter.getDepartment().equals("")) {
                                    filteredResults.add(course);
                                } else {
                                    if (filter.getCourseCodes().contains(course.getCourseCode()) && !filter.getCourseCodes().isEmpty()) {
                                        if (filter.getDepartment().equals("")){
                                            filteredResults.add(course);
                                        } else {
                                            if (course.getCourseDept().equals(filter.getDepartment())) {
                                                filteredResults.add(course);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (filter.getCourseCodes().contains(course.getCourseCode()) && !filter.getCourseCodes().isEmpty()) {
                                    if (filter.getDepartment().equals("")){
                                        filteredResults.add(course);
                                    } else {
                                        if (course.getCourseDept().equals(filter.getDepartment())) {
                                            filteredResults.add(course);
                                        }
                                    }
                                } else {
                                    if (course.getCourseDept().equals(filter.getDepartment())) {
                                        filteredResults.add(course);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        if (filteredResults.isEmpty()) {
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
