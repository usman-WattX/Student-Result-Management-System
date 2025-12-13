public interface ResultCalculator {
    public static final double passMarks = 50;
    
    public double calculateTotal();
    public double calculatePercentage();
    public String calculateGrade();
}
