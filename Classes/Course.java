// Course
//  A􀆩ributes: courseCode, 􀆟tle, creditHours
//  Composi􀆟on: has-a CourseInstructor
//  Sta􀆟c: sta􀆟c int totalCourses
//  Methods: displayCourseDetails()
import java.io.*;

public class Course implements Serializable{
    private String courseCode;
    private String title;
    private int creditHours;
    private CourseInstructor crsInst;
    private static int totalCourses = 0;

    public Course() {
        setCourseCode("------");
        setTitle("------");
        setCreditHours(0);
        setCrsInst(new CourseInstructor());
        totalCourses++;
    }

    public Course(String courseCode, String title, int creditHours, CourseInstructor crsInst) {
        setCourseCode(courseCode);
        setTitle(title);
        setCreditHours(creditHours);
        setCrsInst(crsInst);
        totalCourses++;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course Code Cannot be Empty!");
        }else{
            this.courseCode = courseCode.trim();
        }
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title Cannot be Empty!");
        }else{
            this.title = title.trim();
        }
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        if(creditHours >= 0 && creditHours <= 4){
            this.creditHours = creditHours;
        }else {
            throw new IllegalArgumentException("Credit Hours Must be Between 0 and 4!");
        }
    }   

    public CourseInstructor getCrsInst() {
        return crsInst;
    }

    public void setCrsInst(CourseInstructor crsInst) {
        if (crsInst == null) {
            this.crsInst = new CourseInstructor();
        }else{
            this.crsInst = crsInst;
        }
    }

    public int getTotalCourses(){
        return totalCourses;
    }

    public String toString() {
        return ("COURSE DETAILS: \nCourse Code: " + courseCode + 
    " | Title: " + title + 
    " | Credit Hours: " + creditHours + 
    " | Instructor: " + crsInst.getName());
    }

    public void displayCourseDetails(){
        System.out.println("COURSE DETAILS: \nCourse Code: " + courseCode + 
    " | Title: " + title + 
    " | Credit Hours: " + creditHours + 
    " | Instructor: " + crsInst.getName());
    }
}
