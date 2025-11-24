public class ScienceStudent extends Student {
    private String labGroup;

    public ScienceStudent() {
        super();
        labGroup = null;
    }

    public ScienceStudent(String studentId, String name, String program, Transcript t, String labGroup) {
        super(studentId, name, program, t);
        this.labGroup = labGroup;
    }

    public void setLabGroup(String labGroup) {
        this.labGroup = labGroup;
    }

    public String getLabGroup() {
        return labGroup;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nLab Group: " + labGroup;
    }
}