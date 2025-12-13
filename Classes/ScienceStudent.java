public class ScienceStudent extends Student {
    private String labGroup;

    public ScienceStudent() {
        super();
        setLabGroup("Unknown");
    }

    public ScienceStudent(String name, String program, Transcript t, boolean feePaid, String labGroup) {
        super(name, program, t, feePaid);
        setLabGroup(labGroup);
    }

    public void setLabGroup(String labGroup) {
        if (labGroup == null || labGroup.trim().isEmpty()) {
            throw new IllegalArgumentException("Lab Group cannot be empty");
        } else {
            this.labGroup = labGroup.trim();
        }
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
