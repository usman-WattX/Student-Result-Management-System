// CourseInstructor
//  A􀆩ributes: name, qualifica􀆟on
//  Exists only as part of a Course

public class CourseInstructor {
    private String name;
    private String qualification;

    public CourseInstructor() {
        name = "";
        qualification = "";
    }

    public CourseInstructor(String name, String qualification) {
        this.name = name;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String toString() {
        return "COURSE INSTRUCTOR \nName = " + name + 
        "| Qualification = " + qualification;
    }
}
