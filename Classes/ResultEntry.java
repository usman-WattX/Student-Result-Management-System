public class ResultEntry {
    private Course course;
    private double marksObtained;

    public ResultEntry() {
        course = null;
        marksObtained = 0.0;
    }

    public ResultEntry(Course course, double marksObtained) {
        this.course = course;
        this.marksObtained = marksObtained;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setMarksObtained(double marksObtained) {
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
