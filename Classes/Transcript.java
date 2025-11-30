import java.util.*;

public class Transcript {
    private ArrayList<ResultEntry> results;

    public Transcript() {
        results = new ArrayList<ResultEntry>();
    }

    public Transcript(ArrayList<ResultEntry> results) {
        this.results = results;
    }

    public void setResults(ArrayList<ResultEntry> results) {
        this.results = results;
    }

    public ArrayList<ResultEntry> getResults() {
        return results;
    }

    public void addResultEntry(ResultEntry r) {
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
        int creditHours = 0;
        for (ResultEntry r : results) {
            creditHours = r.getCourse().getCreditHours();
            totalMarks += r.getMarksObtained() * creditHours;
            totalCredit += creditHours;
        }
        double average = totalMarks / totalCredit;
        double gpa4Scale = (average / 100) * 4;
        return gpa4Scale;
    }

    @Override
    public String toString() {
        if (results.isEmpty()) {
            return "Transcript is Empty.";
        }

        String s = "Results:\n";
        for (ResultEntry r : results) {
            s = s + "Course Code: " + r.getCourse().getCourseCode()
                    + "\nCourse: " + r.getCourse().getTitle()
                    + "\nCredit Hours: " + r.getCourse().getCreditHours()
                    + "\nMarks Obtained: " + r.getMarksObtained();
        }
        s = s + "\nTotal Marks: " + getTotalMarks();
        s = s + "\nGPA: " + String.format("%.2f", getGPA()) + "\n";

        return s;
    }

}
