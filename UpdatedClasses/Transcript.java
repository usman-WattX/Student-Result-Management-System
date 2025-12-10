import java.util.*;
import java.io.*;

public class Transcript implements Serializable{
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
        }
        if (r.getCourse() == null) {
            throw new IllegalArgumentException("Course in ResultEntry cannot be null");
        }
        if (r.getMarksObtained() < 0 || r.getMarksObtained() > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
        results.add(r);
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
        double totalMarks = 0.0;
        int totalCredit = 0;
        for (ResultEntry r : results) {
            int creditHours = r.getCourse().getCreditHours();
            totalMarks += r.getMarksObtained() * creditHours;
            totalCredit += creditHours;
        }
        double average = totalMarks / totalCredit;
        return (average / 100) * 4;
    }

    @Override
    public String toString() {
        if (results.isEmpty()) {
            return "Transcript is Empty.";
        }

        String s = "\nResults:\n";
        for (ResultEntry r : results) {
            s += "Course Code: " + r.getCourse().getCourseCode()
                + " | Course: " + r.getCourse().getTitle()
                + " | Credit Hours: " + r.getCourse().getCreditHours()
                + " | Marks Obtained: " + r.getMarksObtained() + "\n";
        }
        s += "Total Marks: " + getTotalMarks();
        s += "\nGPA: " + String.format("%.2f", getGPA()) + "\n";

        return s;
    }
}
