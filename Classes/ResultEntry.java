import java.io.*;

public class ResultEntry implements Serializable{
    private Course course;
    private double marksObtained;

    public ResultEntry() {
        setCourse(null);
        setMarksObtained(0.0);
    }

    public ResultEntry(Course course, double marksObtained) {
        setCourse(course);
        setMarksObtained(marksObtained);
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setMarksObtained(double marksObtained) {
        if (marksObtained < 0 || marksObtained > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
        this.marksObtained = marksObtained;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    @Override
    public String toString() {
        return "Course: " + course.getTitle() +
                " | Marks Obtained: " + marksObtained +
                " | Credit Hours: " + course.getCreditHours();
    }
}
