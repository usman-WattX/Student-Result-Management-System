import java.io.*;

public abstract class Student implements ResultCalculator, Serializable{
    protected String studentId;
    protected String name;
    protected String program;
    protected Transcript t;
    protected boolean feePaid;
    private static int studentCounter = 0;

    public Student() {
        studentCounter++;
        studentId = "S" + (studentCounter);
        setName("Unknown");
        setProgram("Unknown");
        setT(null);
        setFeePaid(false);
    }

    public Student(String name, String program, Transcript t, boolean feePaid) {
        studentCounter++;
        studentId = "S" + (studentCounter);
        setName(name);
        setProgram(program);
        setT(t);
        setFeePaid(feePaid);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        } 
        else {
            this.name = name.trim();
        }
    }

    public void setProgram(String program) {
        if (program == null || program.trim().isEmpty()) {
            throw new IllegalArgumentException("Program cannot be empty");
        } 
        else if (!program.equalsIgnoreCase("Science") && !program.equalsIgnoreCase("Arts") && !program.equalsIgnoreCase("Engineering")) {
            throw new IllegalArgumentException("Program must be Science, Arts or Engineering");
        } 
        else {
            this.program = program.trim();
        }
    }

    public void setT(Transcript t) {
        if (t == null) {
            this.t = new Transcript();
        } 
        else {
            this.t = t;
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public Transcript getT() {
        return t;
    }

    public void addCourse(Course c) {
        if (c == null) {
            throw new IllegalArgumentException("Course cannot be null");
        } else {
            ResultEntry r = new ResultEntry(c, 0);
            t.addResultEntry(r);
        }
    }

    public boolean isFeePaid() {
        return feePaid;
    }
    public void setFeePaid(boolean feePaid) {
        this.feePaid = feePaid;
    }

    public double calculateGPA() {
        return t.getGPA();
    }

    public double calculateTotal() {
        return t.getTotalMarks();
    }

    public double calculatePercentage() {
        int totalCrs = t.getResults().size();
        if (totalCrs == 0) {
            return 0.0;
        } else {
            return (calculateTotal() / (totalCrs * 100)) * 100;
        }
    }

    public String calculateGrade() {
        double percent = calculatePercentage();
        if (percent >= 85)
            return "A";
        else if (percent >= 80)
            return "A-";
        else if (percent >= 75)
            return "B+";
        else if (percent >= 71)
            return "B";
        else if (percent >= 68)
            return "B-";
        else if (percent >= 64)
            return "C+";
        else if (percent >= 61)
            return "C";
        else if (percent >= 58)
            return "C-";
        else if (percent >= 54)
            return "D+";
        else if (percent >= passMarks)
            return "D";
        else
            return "F";
    }

    @Override
    public String toString() {
        String feeStatus = "";
        if (feePaid) {
            feeStatus = "Paid";
        }else{
            feeStatus = "Due";
        }

        return "Student ID: " + studentId +
                "\nName: " + name +
                "\nProgram: " + program +
                "\nTranscript Details: " + t.toString() +
                "Fee Status: " + feeStatus;
    }
}
