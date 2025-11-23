// Course
//  A􀆩ributes: courseCode, 􀆟tle, creditHours
//  Composi􀆟on: has-a CourseInstructor
//  Sta􀆟c: sta􀆟c int totalCourses
//  Methods: displayCourseDetails()

public class Course {
    private String courseCode;
    private String title;
    private int creditHours;
    private CourseInstructor crsInst;
    public static int totalCourses = 0;

    public Course() {
        courseCode = "";
        title = "";
        creditHours = 0;
        crsInst = new CourseInstructor();
    }

    public Course(String courseCode, String title, int creditHours, CourseInstructor crsInst) {
        this.courseCode = courseCode;
        this.title = title;
        if(creditHours >= 0 && creditHours <= 4){
            this.creditHours = creditHours;
        }else if(creditHours < 0){
            System.out.println("Credit Hours can't be less than Zero!");
        }else{
            System.out.println("Credit Hours can't be more than Four!");
        }
        this.crsInst = crsInst;
        totalCourses++;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreditHours() {
        return creditHours;
    }
    public void setCreditHours(int creditHours) {
        if(creditHours >= 0 && creditHours <= 4){
            this.creditHours = creditHours;
        }else if(creditHours < 0){
            System.out.println("Credit Hours can't be less than Zero!");
        }else{
            System.out.println("Credit Hours can't be more than Four!");
        }
    }   

    public CourseInstructor getCrsInst() {
        return crsInst;
    }

    public void setCrsInst(CourseInstructor crsInst) {
        this.crsInst = crsInst;
    }
    
    //Display keliye yaha toString() method bnana ya displayCourseDetails()??


    // public String toString() {
    //     return ("COURSE DETAILS 
    // \nCourse Code = " + courseCode + 
    // " | Title = " + title + 
    // " | Credit Hours = " + creditHours + 
    // " | Instructor = " + crsInst.getName());
    // }

    // public void displayCourseDetails(){
    //     System.out.println("COURSE DETAILS \nCourse Code = " + courseCode + 
    // " | Title = " + title + 
    // " | Credit Hours = " + creditHours + 
    // " | Instructor = " + crsInst.getName());
    // }
}
