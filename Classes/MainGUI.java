import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI {
    public static void main(String[] args) {

        // ---------------- Data Stores ----------------
        DataStore<RecordList<Student>> studentStore = new DataStore<>();
        DataStore<RecordList<Course>> courseStore = new DataStore<>();

        ArrayList<RecordList<Student>> loadedStudents = studentStore.loadFromFile("students.dat");
        ArrayList<RecordList<Course>> loadedCourses = courseStore.loadFromFile("courses.dat");

        RecordList<Student> students;
        RecordList<Course> courses;

        if (loadedStudents.isEmpty()){
            students = new RecordList<>();
        }else{
            students = loadedStudents.get(0);
        }
        if (loadedCourses.isEmpty()){
            courses = new RecordList<>();
        }else{
            courses = loadedCourses.get(0);
            Course.initilaCourseCount(courses.getItems().size());
        }

        // Preload data if empty
        if (students.getItems().isEmpty()) {
            preloadData(students, courses, studentStore, courseStore);
        }

        // ---------------- GUI ----------------
        JFrame frame = new JFrame("Student Result Management System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------------- STUDENTS TAB ----------------
        String[] studentCols = { "ID", "Name", "Program", "GPA", "Grade" };
        DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0);
        JTable studentTable = new JTable(studentModel);

        for (Student s : students.getItems()) {
            if (s.isFeePaid()) {
                studentModel.addRow(new Object[] {
                    s.getStudentId(),
                    s.getName(),
                    s.getProgram(),
                    String.format("%.2f", s.calculateGPA()),
                    s.calculateGrade()
            });
            }else{
                studentModel.addRow(new Object[] {
                    s.getStudentId(),
                    s.getName(),
                    s.getProgram(),
                    "Fee Pending",
                    "---"
            });
            } 
        }

        JPanel studentPanel = new JPanel(new BorderLayout());
        JScrollPane p = new JScrollPane(studentTable);
        studentPanel.add(p, BorderLayout.CENTER);
        JPanel sBtns = new JPanel();
        JButton viewTranscriptBtn = new JButton("View Transcript");
        JButton addStudentBtn = new JButton("Add Student");
        JButton delStudentBtn = new JButton("Delete Student");
        JButton payFeeBtn = new JButton("Pay Fee");
        sBtns.add(viewTranscriptBtn);
        sBtns.add(addStudentBtn);
        sBtns.add(delStudentBtn);
        sBtns.add(payFeeBtn);
        studentPanel.add(sBtns, BorderLayout.SOUTH);

        // ---------------- COURSES TAB ----------------
        String[] courseCols = {"Code", "Title", "Credits", "Instructor" };
        DefaultTableModel courseModel = new DefaultTableModel(courseCols, 0);
        JTable courseTable = new JTable(courseModel);

        for (Course c : courses.getItems()) {
            courseModel.addRow(new Object[] {
                    c.getCourseCode(),
                    c.getTitle(),
                    c.getCreditHours(),
                    c.getCrsInst().getName()
            });
        }

        JPanel coursePanel = new JPanel(new BorderLayout());
        JScrollPane courseScroll = new JScrollPane(courseTable);
        coursePanel.add(courseScroll, BorderLayout.CENTER);
        JButton addCourseBtn = new JButton("Add Course");
        JButton delCourseBtn = new JButton("Delete Course");
        JLabel totalCrsLbl = new JLabel("Total Courses: " + Course.getTotalCourses());
        JPanel cBtns = new JPanel();
        cBtns.add(addCourseBtn);
        cBtns.add(delCourseBtn);
        cBtns.add(totalCrsLbl);
        coursePanel.add(cBtns, BorderLayout.SOUTH);

        // ---------------- TABS ----------------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", studentPanel);
        tabs.addTab("Courses", coursePanel);
        frame.add(tabs);
        frame.setVisible(true);

        // ---------------- StudentBtnListeners ----------------
        ViewTranscriptListener viewListener = new ViewTranscriptListener(studentTable, students);
        viewTranscriptBtn.addActionListener(viewListener);

        AddStudentListener addListener = new AddStudentListener(frame, studentModel, students, studentStore, courses);
        addStudentBtn.addActionListener(addListener);

        DeleteStudentListener deleteListener = new DeleteStudentListener(studentTable, studentModel, students, studentStore);
        delStudentBtn.addActionListener(deleteListener);

        PayFeeListener payFeeListener = new PayFeeListener(studentTable, studentModel, students, studentStore);
        payFeeBtn.addActionListener(payFeeListener);

        // ---------------- CourseBtnListeners ----------------
        AddCourseListener addCourseListener = new AddCourseListener(frame, courseModel, courses, courseStore, totalCrsLbl);
        addCourseBtn.addActionListener(addCourseListener);

        DeleteCourseListener deleteCourseListener = new DeleteCourseListener(courseTable, courseModel, courses, courseStore, totalCrsLbl);
        delCourseBtn.addActionListener(deleteCourseListener);
    }

    public static void preloadData(RecordList<Student> students, RecordList<Course> courses,
        DataStore<RecordList<Student>> studentStore, DataStore<RecordList<Course>> courseStore) {

        CourseInstructor csInstructor = new CourseInstructor("Dr. Ahmed", "PhD");
        CourseInstructor phyInstructor = new CourseInstructor("Prof. Khan", "MSc");
        CourseInstructor mathInstructor = new CourseInstructor("Dr. Sara", "PhD");

        Course cs = new Course("CS101", "Java Programming", 4, csInstructor);
        Course phy = new Course("PHY101", "Physics", 3, phyInstructor);
        Course math = new Course("MATH101", "Calculus", 4, mathInstructor);

        courses.addItem(cs);
        courses.addItem(phy);
        courses.addItem(math);

        Transcript t1 = new Transcript();
        t1.addResultEntry(new ResultEntry(cs, 85));
        t1.addResultEntry(new ResultEntry(phy, 75));
        t1.addResultEntry(new ResultEntry(math, 90));
        Student s1 = new ScienceStudent("Ali Raza", "Science", t1, true, "G1");

        Transcript t2 = new Transcript();
        t2.addResultEntry(new ResultEntry(cs, 78));
        t2.addResultEntry(new ResultEntry(phy, 82));
        t2.addResultEntry(new ResultEntry(math, 70));
        Student s2 = new ArtsStudent("Hassan Ahmed", "Arts", t2, true,"Painting");

        Transcript t3 = new Transcript();
        t3.addResultEntry(new ResultEntry(cs, 65));
        t3.addResultEntry(new ResultEntry(phy, 72));
        t3.addResultEntry(new ResultEntry(math, 68));
        Student s3 = new EngineeringStudent("Usman Khalid", "Engineering", t3, true,"TechCorp");

        Transcript t4 = new Transcript();
        t4.addResultEntry(new ResultEntry(cs, 88));
        t4.addResultEntry(new ResultEntry(phy, 91));
        t4.addResultEntry(new ResultEntry(math, 85));
        Student s4 = new ScienceStudent("Ahsan Farooq", "Science", t4, false,"G2");

        Transcript t5 = new Transcript();
        t5.addResultEntry(new ResultEntry(cs, 80));
        t5.addResultEntry(new ResultEntry(phy, 76));
        t5.addResultEntry(new ResultEntry(math, 79));
        Student s5 = new ArtsStudent("Bilal Tariq", "Arts", t5, true,"Music");

        students.addItem(s1);
        students.addItem(s2);
        students.addItem(s3);
        students.addItem(s4);
        students.addItem(s5);

        ArrayList<RecordList<Student>> tempS = new ArrayList<>();
        tempS.add(students);
        studentStore.saveToFile("students.dat", tempS);

        ArrayList<RecordList<Course>> tempC = new ArrayList<>();
        tempC.add(courses);
        courseStore.saveToFile("courses.dat", tempC);
    }
}
