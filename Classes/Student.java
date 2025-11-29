// 1. Student (Abstract Class)
//  AƩributes: studentId, name, program
//  ComposiƟon: has-a Transcript
//  StaƟc: staƟc int totalStudents
//  Methods: addCourse(), calculateGPA(), displayResults()
//  Subclasses: ScienceStudent, ArtsStudent, EngineeringStudent
public abstract class Student {
    protected String studentId;
    protected String name;
    protected String program;
    protected Transcript t;
    public static int totalStudents = 0;

    public Student() {
        studentId = null;
        name = null;
        program = null;
        t = new Transcript();
    }

    public Student(String studentId, String name, String program, Transcript t) {
        this.studentId = studentId;
        this.name = name;
        this.program = program;
        this.t = t;
        totalStudents++;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public void setT(Transcript t) {
        this.t = t;
    }

    public Transcript getT() {
        return t;
    }

    public void addCourse(Course c) {
        ResultEntry r = new ResultEntry(c, 0);
        t.addResultEntry(r);
    }

    public double calculateGPA() {
        return t.getGPA();
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
                "\nName: " + name +
                "\nProgram: " + program +
                "\nTranscript Details: " + t.toString();
    }

}
