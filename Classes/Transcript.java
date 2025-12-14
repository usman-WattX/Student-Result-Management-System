import java.io.*;
import java.util.*;

public class Transcript implements Serializable {
    private ArrayList<ResultEntry> results;

    public Transcript() {
        results = new ArrayList<>();
    }

    public Transcript(ArrayList<ResultEntry> results) {
        setResults(results);
    }

    public void setResults(ArrayList<ResultEntry> results) {
        if (results == null) {
            this.results = new ArrayList<>();
        } else {
            this.results = results;
        }
    }

    public ArrayList<ResultEntry> getResults() {
        return results;
    }

    public void addResultEntry(ResultEntry r) {
        if (r == null || r.getCourse() == null) {
            throw new IllegalArgumentException("ResultEntry or Course cannot be null");
        }
        if (r.getMarksObtained() < 0 || r.getMarksObtained() > 100) {
            throw new IllegalArgumentException("Marks must be 0-100");
        }
        results.add(r);
    }

    // Calculate GPA **per course**
    public ArrayList<Double> getCourseGPAs() {
        ArrayList<Double> gpas = new ArrayList<>();
        for (ResultEntry r : results) {
            double courseGPA = (r.getMarksObtained() / 100.0) * 4; // 0-4 scale
            gpas.add(courseGPA);
        }
        return gpas;
    }

    // Total Marks
    public double getTotalMarks() {
        double total = 0;
        for (ResultEntry r : results) {
            total += r.getMarksObtained();
        }
        return total;
    }

    @Override
    public String toString() {
        if (results.isEmpty()) return "Transcript is Empty.";

        String s = "Results:\n";
        for (ResultEntry r : results) {
            s = s + "Course Code: " + r.getCourse().getCourseCode()
                + " | Course: " + r.getCourse().getTitle()
                + " | Credit Hours: " + r.getCourse().getCreditHours()
                + " | Marks: " + r.getMarksObtained()
                + " | GPA: " + String.format("%.2f", (r.getMarksObtained() / 100.0) * 4) + "\n";
        }
        s = s + "Total Marks: " + getTotalMarks() + "\n";
        return s;
    }
}
