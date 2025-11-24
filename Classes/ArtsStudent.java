public class ArtsStudent extends Student {
    private String majorArtForm;

    public ArtsStudent() {
        super();
        majorArtForm = null;
    }

    public ArtsStudent(String studentId, String name, String program, Transcript t, String majorArtForm) {
        super(studentId, name, program, t);
        this.majorArtForm = majorArtForm;
    }

    public void setMajorArtForm(String majorArtForm) {
        this.majorArtForm = majorArtForm;
    }

    public String getMajorArtForm() {
        return majorArtForm;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMajor Art Form: " + majorArtForm;
    }
}