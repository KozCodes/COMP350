package edu.gcc.comp350;


import java.sql.Time;
import java.util.*;


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
        //Uses TF-IDF and NLP to calculate the weight of the given query against the course
        //database, returning a list of courses that are sorted by weight
        //of relevance to the query.


        //3 main search queries usually - course title, course code, professor name
        boolean isKeyword = false;
        boolean isCourseCode = false;
        boolean isProf = false;


        //algorithm to check what type query is - don't need to perform TF-IDF
        //multiple times if query is not of a certain type


        query = query.toUpperCase();


        //easiest tell - if theres recognizable digits somewhere, then its a course code query
        if (Character.isDigit(query.charAt(0)) || Character.isDigit(query.charAt(5))) {
            isCourseCode = true;
        } else {
            //harder - if the query contains a professor name, then it is a professor query
            for (int i = 0; i < RefactoredMain.professors.size(); i++) {
                if (RefactoredMain.professors.get(i).getName().contains(query)) {
                    isProf = true;
                    break;
                }
            }


            //if still nothing, do the same thing but for course titles
            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                if (RefactoredMain.courses.get(i).getCourseTitle().contains(query)) {
                    isKeyword = true;
                    break;
                }
            }
        }


        if (isCourseCode || isProf || isKeyword) {
            TFIDFSearch(isCourseCode, isProf, isKeyword, query);
        }
        //if search results is still empty, don't call it quits just yet, use NLP
        //to parse the query and check the dictionary and collect any close
        //enough matches to the query


        //4 most common errors in spelling: missing letter, extra letter, swapped letters, and
        //incorrect letters (subsets - missing spaces, extra spaces)


        if (searchResults.isEmpty()) {
            List<String> manipulations = new ArrayList<>();


            //generate all possible manipulations of the query - limitations - can only generate one letter manipulations


            //if proof of concept works, this method can be expanded to include multiple letter manipulations

            //missing letters - add a letter to the query
            for (int i = 0; i < query.toUpperCase().length(); i++) {
                for (int j = 0; j < 26; j++) {
                    StringBuilder sb = new StringBuilder(query);
                    sb.insert(i, (char) ('A' + j));
                    if (!manipulations.contains(sb.toString())) {
                        manipulations.add(sb.toString());
                    }
                }
                //subsection: as last resort, try adding spaces to query
            }

            //extra letters - remove a letter from the query
            //Github copilot generated code for these manipulations


            for (int i = 0; i < query.toUpperCase().length(); i++) {
                StringBuilder sb = new StringBuilder(query);
                sb.deleteCharAt(i);
                if (!manipulations.contains(sb.toString())) {
                    manipulations.add(sb.toString());
                }
            }




            //swapped letters - swap two letters in the query


            for (int i = 0; i < query.length(); i++) {
                char temp = query.charAt(i);
                for (int j = 0; j < query.length(); j++) {
                   char temp2 = query.charAt(j);
                    StringBuilder sb = new StringBuilder(query);
                    sb.setCharAt(i, temp2);
                    sb.setCharAt(j, temp);
                    if (!manipulations.contains(sb.toString())) {
                        manipulations.add(sb.toString());
                    }
                }
            }

            //incorrect letters - replace a letter in the query with another letter


            for (int i = 0; i < query.toUpperCase().length(); i++) {
                StringBuilder sb = new StringBuilder(query);
                for (int j = 0; j < 26; j++) {
                    sb.replace(i, i+ 1, ('A' + j) + "");
                    if (!manipulations.contains(sb.toString())) {
                        manipulations.add(sb.toString());
                    }
                }
                //subsection: as last resort, try adding spaces to query
                sb.replace(i, i+ 1, " ");
                if (!manipulations.contains(sb.toString())) {
                    manipulations.add(sb.toString());
                }
            }
             

            //remove any manipulations that are gibberish, or, anything not in our vocabulary
            manipulations.replaceAll(s -> s.replaceAll("\\s+", "").trim());
            RefactoredMain.Dictionary.replaceAll(s -> s.replaceAll("\\s+", "").trim());
            Set<String> dictionarySet = new HashSet<>(RefactoredMain.Dictionary);
            manipulations.removeIf(item -> !dictionarySet.contains(item));

            //if there are manipulations not gibberish, try searching for them
            if (!manipulations.isEmpty()) {
                for (int i = 0; i < manipulations.size(); i++) {
                    if (Character.isDigit(manipulations.get(i).charAt(0)) || Character.isDigit(manipulations.get(i).charAt(5))) {
                        isCourseCode = true;
                    } else {
                        //harder - if the query contains a professor name, then it is a professor query
                        for (int j = 0; j < RefactoredMain.professors.size(); j++) {
                            if (RefactoredMain.professors.get(j).getName().contains(manipulations.get(i))) {
                                isProf = true;
                                break;
                            }
                        }


                        //if still nothing, do the same thing but for course titles
                        for (int j = 0; j < RefactoredMain.courses.size(); j++) {
                            if (RefactoredMain.courses.get(j).getCourseTitle().contains(manipulations.get(i))) {
                                isKeyword = true;
                                break;
                            }
                        }
                    }

                    TFIDFSearch(isCourseCode, isProf, isKeyword, manipulations.get(i));
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


    protected void TFIDFSearch(boolean isCourseCode, boolean isProf, boolean isKeyword, String newquery) {


        if (isKeyword) {
            List<String> splitQuery = Arrays.asList(newquery.split(" "));
            splitQuery.removeIf(num -> RefactoredMain.stopwordList.contains(num));


            //weight all classes based on query
            HashMap<Course, Double> queryWeights = new HashMap();


            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                //initialize the course to already have an int value
                queryWeights.put(RefactoredMain.courses.get(i), 0.0);


                for (int j = 0; j < splitQuery.size(); j++) {
                    List<String> title = Arrays.asList(RefactoredMain.courses.get(i).getCourseTitle().toUpperCase().split(" "));
                    double m = 0.0;


                    for (int k = 0; k < title.size(); k++) {
                        if (title.get(k).equals(splitQuery.get(j))) {
                            m++;
                        }
                    }


                    double weight = m / title.size();


                    //TF - if the term is in the title, weight it by the amount of times it shows up and add it to the weight
                    queryWeights.put(RefactoredMain.courses.get(i), queryWeights.get(RefactoredMain.courses.get(i)) + weight);
                }
            }


            //remove all courses that have a weight of 0, because they are not relevant to the query and
            //have no need to calculate the IDF
            HashMap<Course, Double> queryWeightsCopy = new HashMap<>();


            for (Course course : queryWeights.keySet()) {
                if (queryWeights.get(course) > 0) {
                    queryWeightsCopy.put(course, queryWeights.get(course));
                }
            }


            int IDFcount = 0;
            //IDF - count how many times the term shows up across the database, then multiply the weight
            for (int i = 0; i < splitQuery.size(); i++) {
                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getCourseTitle().toUpperCase().contains(splitQuery.get(i))) {
                        IDFcount++;
                    }
                }


                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getCourseTitle().toUpperCase().contains(splitQuery.get(i))) {
                        double weight = queryWeightsCopy.get(course) * Math.log(RefactoredMain.courses.size() / IDFcount);
                        queryWeightsCopy.put(course, weight);
                    }
                }


                IDFcount = 0;
            }


            //sort the courses by weight, highest to lowest - do not count any courses
            //that have a weight of 0


            for (Course course : queryWeightsCopy.keySet()) {
                searchResults.add(course);
            }


            //do a binary sort on the searchResults list


            //binary search code generated with the assistance of Github Copilot
            for (int i = 0; i < searchResults.size(); i++) {
                for (int j = 0; j < searchResults.size() - 1; j++) {
                    if (queryWeights.get(searchResults.get(j)) < queryWeights.get(searchResults.get(j + 1))) {
                        Course temp = searchResults.get(j);
                        searchResults.set(j, searchResults.get(j + 1));
                        searchResults.set(j + 1, temp);
                    }
                }
            }
        } else if (isProf) {
            List<String> splitQuery = Arrays.asList(newquery.split(" "));
            splitQuery.removeIf(num -> RefactoredMain.stopwordList.contains(num));


            //weight all classes based on query
            HashMap<Course, Double> queryWeights = new HashMap();


            //IF less helpful here but still want to do as it weeds out irrelevant courses
            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                //initialize the course to already have an int value
                queryWeights.put(RefactoredMain.courses.get(i), 0.0);


                for (int j = 0; j < splitQuery.size(); j++) {
                    List<String> title = Arrays.asList(RefactoredMain.courses.get(i).getProfessor().getName().toUpperCase().split(" "));
                    double m = 0.0;


                    for (int k = 0; k < title.size(); k++) {
                        if (title.get(k).equals(splitQuery.get(j))) {
                            m++;
                        }
                    }


                    double weight = m / title.size();


                    //TF - if the term is in the title, weight it by the amount of times it shows up and add it to the weight
                    queryWeights.put(RefactoredMain.courses.get(i), queryWeights.get(RefactoredMain.courses.get(i)) + weight);
                }
            }


            //remove all courses that have a weight of 0, because they are not relevant to the query and
            //have no need to calculate the IDF
            HashMap<Course, Double> queryWeightsCopy = new HashMap<>();


            for (Course course : queryWeights.keySet()) {
                if (queryWeights.get(course) > 0) {
                    queryWeightsCopy.put(course, queryWeights.get(course));
                }
            }


            int IDFcount = 0;
            //IDF - count how many times the term shows up across the database, then multiply the weight
            for (int i = 0; i < splitQuery.size(); i++) {
                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getProfessor().getName().toUpperCase().contains(splitQuery.get(i))) {
                        IDFcount++;
                    }
                }


                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getProfessor().getName().toUpperCase().contains(splitQuery.get(i))) {
                        double weight = queryWeightsCopy.get(course) * Math.log(RefactoredMain.courses.size() / IDFcount);
                        queryWeightsCopy.put(course, weight);
                    }
                }


                IDFcount = 0;
            }


            //sort the courses by weight, highest to lowest - do not count any courses
            //that have a weight of 0


            for (Course course : queryWeightsCopy.keySet()) {
                searchResults.add(course);
            }


            //do a binary sort on the searchResults list


            //binary search code generated with the assistance of Github Copilot
            for (int i = 0; i < searchResults.size(); i++) {
                for (int j = 0; j < searchResults.size() - 1; j++) {
                    if (queryWeights.get(searchResults.get(j)) < queryWeights.get(searchResults.get(j + 1))) {
                        Course temp = searchResults.get(j);
                        searchResults.set(j, searchResults.get(j + 1));
                        searchResults.set(j + 1, temp);
                    }
                }
            }
        } else if (isCourseCode) {
            List<String> splitQuery = Arrays.asList(newquery.split(" "));
            splitQuery.removeIf(num -> RefactoredMain.stopwordList.contains(num));


            //weight all classes based on query
            HashMap<Course, Double> queryWeights = new HashMap();


            for (int i = 0; i < RefactoredMain.courses.size(); i++) {
                //initialize the course to already have an int value
                queryWeights.put(RefactoredMain.courses.get(i), 0.0);


                for (int j = 0; j < splitQuery.size(); j++) {
                    List<String> title = Arrays.asList(RefactoredMain.courses.get(i).getCourseCode().split(" "));
                    double m = 0.0;


                    for (int k = 0; k < title.size(); k++) {
                        if (title.get(k).equals(splitQuery.get(j))) {
                            m++;
                        }
                    }


                    double weight = m / title.size();


                    //TF - if the term is in the title, weight it by the amount of times it shows up and add it to the weight
                    queryWeights.put(RefactoredMain.courses.get(i), queryWeights.get(RefactoredMain.courses.get(i)) + weight);
                }
            }


            //remove all courses that have a weight of 0, because they are not relevant to the query and
            //have no need to calculate the IDF
            HashMap<Course, Double> queryWeightsCopy = new HashMap<>();


            for (Course course : queryWeights.keySet()) {
                if (queryWeights.get(course) > 0) {
                    queryWeightsCopy.put(course, queryWeights.get(course));
                }
            }


            int IDFcount = 0;
            //IDF - count how many times the term shows up across the database, then multiply the weight
            for (int i = 0; i < splitQuery.size(); i++) {
                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getCourseCode().toUpperCase().contains(splitQuery.get(i))) {
                        IDFcount++;
                    }
                }


                for (Course course : queryWeightsCopy.keySet()) {
                    if (course.getCourseCode().toUpperCase().contains(splitQuery.get(i))) {
                        double weight = queryWeightsCopy.get(course) * Math.log(RefactoredMain.courses.size() / IDFcount);
                        queryWeightsCopy.put(course, weight);
                    }
                }


                IDFcount = 0;
            }


            //sort the courses by weight, highest to lowest - do not count any courses
            //that have a weight of 0


            for (Course course : queryWeightsCopy.keySet()) {
                searchResults.add(course);
            }


            //do a binary sort on the searchResults list


            //binary search code generated with the assistance of Github Copilot
            for (int i = 0; i < searchResults.size(); i++) {
                for (int j = 0; j < searchResults.size() - 1; j++) {
                    if (queryWeights.get(searchResults.get(j)) < queryWeights.get(searchResults.get(j + 1))) {
                        Course temp = searchResults.get(j);
                        searchResults.set(j, searchResults.get(j + 1));
                        searchResults.set(j + 1, temp);
                    }
                }
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
            filteredResults.add(course);
        }


        //code generated with the assistance of Github Copilot
        if (!filter.getCourse().contains(RefactoredMain.Days.BLANK)) {
            filteredResults.removeIf(course -> filter.getCourse().contains(course.getCourseDays()));
        }
        if (!filter.getStartTime().contains(Time.valueOf("00:00:00"))) {
            filteredResults.removeIf(course -> filter.getStartTime().contains(course.getStartTime()));
        }


        if (!filter.getEndTime().contains(Time.valueOf("00:00:00"))) {
            filteredResults.removeIf(course -> filter.getEndTime().contains(course.getEndTime()));
        }


        if (!filter.getCourseSession().equals(RefactoredMain.Session.BLANK)) {
            filteredResults.removeIf(course -> course.getSession().equals(filter.getCourseSession()));
        }


        if (!filter.getDepartment().equals("")) {
            filteredResults.removeIf(course -> course.getCourseDept().equals(filter.getDepartment()));
        }


        if (!filter.getCourseCodes().isEmpty()) {
            filteredResults.removeIf(course -> filter.getCourseCodes().contains(course.getCourseCode()));
        }


        if (filter.getYear() != 0000) {
            filteredResults.removeIf(course -> course.getYear() != filter.getYear());
        }


        if (filteredResults.isEmpty()) {
            System.out.println("I'm sorry, we're unable to find anything related to your search. Try modifying your filters or query.");
        }


    }


    protected void Parser() {
        //parse the query and attempt to match it with
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
