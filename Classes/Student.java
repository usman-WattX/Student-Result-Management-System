// 1. Student (Abstract Class)
//  AƩributes: studentId, name, program
//  ComposiƟon: has-a Transcript
//  StaƟc: staƟc int totalStudents
//  Methods: addCourse(), calculateGPA(), displayResults()
//  Subclasses: ScienceStudent, ArtsStudent, EngineeringStudent

public abstract class Student implements ResultCalculator{
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

    public double calculateTotal(){
        return t.getTotalMarks();
    }

    public double calculatePercentage(){
        int totalCrs = t.getResults().size();
        if (totalCrs == 0 ) {
            return 0.0;
        }else{
            double percent = (calculateTotal() / (totalCrs * 100)) * 100;
            return percent;
        }
        
    }

    public String calculateGrade(){
        double percent = calculatePercentage();
        if (percent >= 85) {
            return "A";
        } else if(percent <= 84 && percent >= 80){
            return "A-";
        } else if(percent <= 79 && percent >= 75){
            return "B+";
        } else if(percent <= 74 && percent >= 71){
            return "B";
        } else if(percent <= 70 && percent >= 68){
            return "B-";
        } else if(percent <= 67 && percent >= 64){
            return "C+";
        } else if(percent <= 63 && percent >= 61){
            return "C";
        } else if(percent <= 60 && percent >= 58){
            return "C-";
        } else if(percent <= 57 && percent >= 54){
            return "D+";
        } else if(percent <= 53 && percent >= 50){
            return "D";
        } else{
            return "F";
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
                "\nName: " + name +
                "\nProgram: " + program +
                "\nTranscript Details: " + t.toString();
    }
}
