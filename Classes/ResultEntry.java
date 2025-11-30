public class ResultEntry {
    private Course course;
    private double marksObtained;

    public ResultEntry() {
        course = null;
        marksObtained = 0.0;
    }

    public ResultEntry(Course course, double marksObtained) {
        this.course = course;
        if (marksObtained >= 0 && marksObtained <= 100) {
         this.marksObtained = marksObtained;   
        }else{
            System.out.println("Marks Should not be less than Zero or Greater than 100!");
        }
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setMarksObtained(double marksObtained) {
        if (marksObtained >= 0 && marksObtained <= 100) {
         this.marksObtained = marksObtained;   
        }else{
            System.out.println("Marks Should not be less than Zero or Greater than 100!");
        }
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