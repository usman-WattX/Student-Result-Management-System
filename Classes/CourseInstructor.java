import java.io.*;

public class CourseInstructor implements Serializable {
    private String name;
    private String qualification;

    public CourseInstructor() {
        setName("------");
        setQualification("------");
    }

    public CourseInstructor(String name, String qualification) {
        setName(name);
        setQualification(qualification);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name Cannot Be Empty!");
        }else{
            this.name = name.trim();
        }
    }

    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        if (qualification == null || qualification.trim().isEmpty()) {
            throw new IllegalArgumentException("Qualification Cannot Be Empty!");
        }else{
            this.qualification = qualification.trim();
        }
    }

    public String toString() {
        return ("COURSE INSTRUCTOR: \nName: " + name + 
        " | Qualification: " + qualification);
    }
}
