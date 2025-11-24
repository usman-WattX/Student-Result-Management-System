public class EngineeringStudent extends Student {
    private String internshipCompany;

    public EngineeringStudent() {
        internshipCompany = null;
    }

    public EngineeringStudent(String studentId, String name, String program, Transcript t, String internshipCompany) {
        super(studentId, name, program, t);
        this.internshipCompany = internshipCompany;
    }

    public void setInternshipCompany(String internshipCompany) {
        this.internshipCompany = internshipCompany;
    }

    public String getInternshipCompany() {
        return internshipCompany;
    }

    public String toString() {
        return super.toString() +
                "\nInternship Company: " + internshipCompany;
    }
}