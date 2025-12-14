import java.io.*;
import java.util.*;

public class Transcript implements Serializable {
    private ArrayList<ResultEntry> results;

    public Transcript() {
        results = new ArrayList<ResultEntry>();
    }

    public Transcript(ArrayList<ResultEntry> results) {
        setResults(results); 
    }

    public void setResults(ArrayList<ResultEntry> results) {
        if (results == null) {
            this.results = new ArrayList<ResultEntry>();
        } else {
            this.results = results;
        }
    }

    public ArrayList<ResultEntry> getResults() {
        return results;
    }

    public void addResultEntry(ResultEntry r) {
        if (r == null) {
            throw new IllegalArgumentException("ResultEntry cannot be null");
        } else if (r.getCourse() == null) {
            throw new IllegalArgumentException("Course in ResultEntry cannot be null");
        } else if (r.getMarksObtained() < 0 || r.getMarksObtained() > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        } else {
            results.add(r);
        }
    }

    public double getCourseGPA(ResultEntry r) {
        double marks = r.getMarksObtained();

        if (marks >= 85) {
            return 4.00;
        } else if (marks >= 80) {
            return 3.67;
        } else if (marks >= 75) {
            return 3.33;
        } else if (marks >= 71) {
            return 3.00;
        } else if (marks >= 68) {
            return 2.67;
        } else if (marks >= 64) {
            return 2.33;
        } else if (marks >= 61) {
            return 2.00;
        } else if (marks >= 58) {
            return 1.67;
        } else if (marks >= 54) {
            return 1.30;
        } else if (marks >= 50) {
            return 1.00;
        } else {
            return 0.00;
        }
    }

    public String getCourseGrade(ResultEntry r) {
        double GPA = getCourseGPA(r); 

        if (GPA >= 3.67) {
            return "A";
        } else if (GPA >= 3.34) {
            return "A-";
        } else if (GPA >= 3.01) {
            return "B+";
        } else if (GPA >= 2.67) {
            return "B";
        } else if (GPA >= 2.34) {
            return "B-";
        } else if (GPA >= 2.01) {
            return "C+";
        } else if (GPA >= 1.67) {
            return "C";
        } else if (GPA >= 1.31) {
            return "C-";
        } else if (GPA >= 1.01) {
            return "D+";
        } else if (GPA >= 0.10) {
            return "D";
        } else {
            return "F";
        } 
    }

    public double getTotalMarks() {
        double total = 0.0;
        for (ResultEntry r : results) {
            total += r.getMarksObtained();
        }
        return total;
    }

    public double getGPA() {
        if (results.isEmpty()) {
            return 0.0;
        }

        double totalWeightedGPA = 0.0;
        int totalCredits = 0;

        for (ResultEntry r : results) {
            int creditHours = r.getCourse().getCreditHours();
            totalWeightedGPA += getCourseGPA(r) * creditHours;
            totalCredits += creditHours;
        }

        if (totalCredits == 0) {
            return 0.0;
        } else {
            return totalWeightedGPA / totalCredits;
        }
    }

    @Override
    public String toString() {
        if (results.isEmpty()) return "Transcript is Empty.";

        String s = "Results:\n";
        for (ResultEntry r : results) {
            s = s + "Course Code: " + r.getCourse().getCourseCode()
                + " | Course: " + r.getCourse().getTitle()
                + " | Credit Hours: " + r.getCourse().getCreditHours()
                + " | Marks Obtained: " + r.getMarksObtained() + "\n";
        }
        s = s + "Total Marks: " + getTotalMarks() + "\n";
        s = s + "GPA: " + String.format("%.2f", getGPA()) + "\n";

        return s;
    }
}
