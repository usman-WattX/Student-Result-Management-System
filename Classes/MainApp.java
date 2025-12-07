import java.util.*;

public class MainApp {

    public static void main(String[] args) {
        CourseInstructor ins1 = new CourseInstructor("Dr. Ahmed", "PhD Physics");
        CourseInstructor ins2 = new CourseInstructor("Dr. Fatima", "PhD Chemistry");
        CourseInstructor ins3 = new CourseInstructor("Sir Bilal", "MS Mathematics");
        CourseInstructor ins4 = new CourseInstructor("Dr. Sana", "PhD Biology");
        CourseInstructor ins5 = new CourseInstructor("Sir Ali", "MS Computer Science");
        CourseInstructor ins6 = new CourseInstructor("Ms. Hina", "MA Music");
        CourseInstructor ins7 = new CourseInstructor("Mr. Naveed", "MA Painting");

        
        Course phy = new Course("PHY101", "Physics", 3, ins1);
        Course chem = new Course("CHE101", "Chemistry", 3, ins2);
        Course math = new Course("MTH101", "Mathematics", 4, ins3);
        Course bio = new Course("BIO101", "Biology", 3, ins4);
        Course cs = new Course("CS101", "Computer Science", 3, ins5);
        Course music = new Course("ART101", "Music", 2, ins6);
        Course painting = new Course("ART102", "Painting", 2, ins7);

        Transcript t1 = new Transcript();
        t1.addResultEntry(new ResultEntry(phy, 88));
        t1.addResultEntry(new ResultEntry(chem, 79));
        t1.addResultEntry(new ResultEntry(math, 91));
        ScienceStudent s1 = new ScienceStudent("Ayesha Siddiqui", "Science", t1, "Lab-A");
        Transcript t2 = new Transcript();
        t2.addResultEntry(new ResultEntry(cs, 85));
        t2.addResultEntry(new ResultEntry(math, 90));
        t2.addResultEntry(new ResultEntry(phy, 80));
        EngineeringStudent s2 = new EngineeringStudent("Ali Khan", "Engineering", t2, "TechCorp");


        Transcript t3 = new Transcript();
        t3.addResultEntry(new ResultEntry(music, 92));
        t3.addResultEntry(new ResultEntry(painting, 88));
        ArtsStudent s3 = new ArtsStudent("Sara Ahmed", "Arts", t3, "Music");

        
        Transcript t4 = new Transcript();
        t4.addResultEntry(new ResultEntry(cs, 78));
        t4.addResultEntry(new ResultEntry(phy, 82));
        t4.addResultEntry(new ResultEntry(chem, 75));
        EngineeringStudent s4 = new EngineeringStudent("Hassan Raza", "Engineering", t4, "Innovatech");
        Transcript t5 = new Transcript();
        t5.addResultEntry(new ResultEntry(painting, 90));
        t5.addResultEntry(new ResultEntry(music, 85));
        ArtsStudent s5 = new ArtsStudent("Fatima Noor", "Arts", t5, "Painting");
        RecordList<Student> studentList = new RecordList<>();
        RecordList<Course> courseList = new RecordList<>();
        RecordList<Transcript> transcriptList = new RecordList<>();

        studentList.addItem(s1);
        studentList.addItem(s2);
        studentList.addItem(s3);
        studentList.addItem(s4);
        studentList.addItem(s5);

        courseList.addItem(phy);
        courseList.addItem(chem);
        courseList.addItem(math);
        courseList.addItem(bio);
        courseList.addItem(cs);
        courseList.addItem(music);
        courseList.addItem(painting);

        transcriptList.addItem(t1);
        transcriptList.addItem(t2);
        transcriptList.addItem(t3);
        transcriptList.addItem(t4);
        transcriptList.addItem(t5);

        
        System.out.println("=== STUDENT ACADEMIC REPORTS ===");
        for (Student s : studentList.getItems()) {
            System.out.println(s);
            System.out.println("Total Marks: " + s.calculateTotal());
            System.out.println("Percentage: " + s.calculatePercentage() + "%");
            System.out.println("Grade: " + s.calculateGrade());
            System.out.println("-----------------------------------");
        }
        DataStore<Student> studentStore = new DataStore<>();
        studentStore.saveToFile("students.dat", studentList.getItems());
        System.out.println("\nRecords Saved Successfully!");


        System.out.println("\n=== LOADING RECORDS FROM FILE ===");
        ArrayList<Student> loadedStudents = studentStore.loadFromFile("students.dat");

        for (Student s : loadedStudents) {
            System.out.println(s);
            System.out.println("------------------------------");
        }

        System.out.println("\n===== RECORD LIST: STUDENTS =====");
        studentList.showAllItems();
    }
}
