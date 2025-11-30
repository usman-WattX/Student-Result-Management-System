public class EngineeringStudent extends Student {
    private String internshipCompany;

    public EngineeringStudent() {
        super();
        setInternshipCompany("Unknown");
    }

    public EngineeringStudent(String name, String program, Transcript t, String internshipCompany) {
        super(name, program, t);
        setInternshipCompany(internshipCompany);
    }

    public void setInternshipCompany(String internshipCompany) {
        if (internshipCompany == null || internshipCompany.trim().isEmpty()) {
            throw new IllegalArgumentException("Internship Company cannot be empty");
        } 
        else {
            this.internshipCompany = internshipCompany.trim();
        }
    }

    public String getInternshipCompany() {
        return internshipCompany;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nInternship Company: " + internshipCompany;
    }
}
