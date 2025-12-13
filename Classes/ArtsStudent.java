public class ArtsStudent extends Student {
    private String majorArtForm;

    public ArtsStudent() {
        super();
        setMajorArtForm("Unknown");
    }

    public ArtsStudent(String name, String program, Transcript t, boolean feePaid, String majorArtForm) {
        super(name, program, t, feePaid);
        setMajorArtForm(majorArtForm);
    }

    public void setMajorArtForm(String majorArtForm) {
        if (majorArtForm == null || majorArtForm.trim().isEmpty()) {
            throw new IllegalArgumentException("Major Art Form cannot be empty");
        } 
        else {
            this.majorArtForm = majorArtForm.trim();
        }
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
