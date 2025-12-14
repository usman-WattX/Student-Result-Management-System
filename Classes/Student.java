import java.io.Serializable;

public abstract class Student implements ResultCalculator, Serializable {
    protected String studentId;
    protected String name;
    protected String program;
    protected Transcript t;
    protected boolean feePaid;
    private static int studentCounter = 0;

    public Student(String name, String program, Transcript t, boolean feePaid) {
        studentCounter++;
        studentId = "S" + studentCounter;
        this.name = name;
        this.program = program;
        this.t = t != null ? t : new Transcript();
        this.feePaid = feePaid;
    }

    public double calculateGPA() {
        if (t.getResults().isEmpty())
            return 0.0;

        double total = 0;
        int count = 0;
        for (double gpa : t.getCourseGPAs()) {
            total += gpa;
            count++;
        }
        return total / count; // CGPA
    }

    public String calculateGrade() {
        double cgpa = calculateGPA();
        if (cgpa >= 3.7)
            return "A";
        else if (cgpa >= 3.0)
            return "B";
        else if (cgpa >= 2.0)
            return "C";
        else
            return "F";
    }

    public boolean isFeePaid() {
        return feePaid;
    }

    public void setFeePaid(boolean feePaid) {
        this.feePaid = feePaid;
    }

    public String getName() {
        return name;
    }

    public Transcript getTranscript() {
        return t;
    }
}
