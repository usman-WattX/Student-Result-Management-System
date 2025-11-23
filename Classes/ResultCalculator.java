// ResultCalculator (Interface)
//  Sta􀆟c Member: sta􀆟c final double passMarks = 50;
//  Methods: calculateTotal(), calculatePercentage(), calculateGrade()
//  Implemented by: all Student subclasses

public interface ResultCalculator {
    public static final double passMarks = 50;
    
    public double calculateTotal();
    public double calculatePercentage();
    public String calculateGrade();
}
