import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class MainGUI {

    public static void main(String[] args) {

        DataStore<Student> studentStore = new DataStore<>();
        DataStore<Course> courseStore = new DataStore<>();

        ArrayList<Student> students = studentStore.loadFromFile("students.dat");
        ArrayList<Course> courses = courseStore.loadFromFile("courses.dat");
        if (students.isEmpty()) {
            preloadData(students, courses, studentStore, courseStore);
        }

        JFrame frame = new JFrame("Student Result Management System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // STUDENT TAB
        String[] studentCols = { "ID", "Name", "Program", "GPA", "Grade" };
        DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0);
        JTable studentTable = new JTable(studentModel);

        for (Student s : students) {
            studentModel.addRow(new Object[] {
                    s.getStudentId(),
                    s.getName(),
                    s.getProgram(),
                    String.format("%.2f", s.calculateGPA()),
                    s.calculateGrade()
            });
        }

        BorderLayout b = new BorderLayout();
        JPanel studentPanel = new JPanel(b);
        JScrollPane p = new JScrollPane(studentTable);
        studentPanel.add(p, BorderLayout.CENTER);
        JPanel sBtns = new JPanel();
        JButton addStudentBtn = new JButton("Add Student");
        JButton delStudentBtn = new JButton("Delete Student");
        sBtns.add(addStudentBtn);
        sBtns.add(delStudentBtn);
        studentPanel.add(sBtns, BorderLayout.SOUTH);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", studentPanel);

        // COURSES TAB
        String[] courseCols = { "Code", "Title", "Credits", "Instructor" };
        DefaultTableModel courseModel = new DefaultTableModel(courseCols, 0);
        JTable courseTable = new JTable(courseModel);

        for (Course c : courses) {
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
        JPanel cBtns = new JPanel();
        cBtns.add(addCourseBtn);
        cBtns.add(delCourseBtn);
        coursePanel.add(cBtns, BorderLayout.SOUTH);
        tabs.addTab("Courses", coursePanel);

        frame.add(tabs);
        frame.setVisible(true);

        // =====Add Student=====
        AddStudentListener addListener = new AddStudentListener(
                frame, studentModel, students, studentStore, courses);
        addStudentBtn.addActionListener(addListener);

        // =====DELETING STUDENT=====
        DeleteStudentListener deleteListener = new DeleteStudentListener(studentTable, studentModel, students,
                studentStore);
        delStudentBtn.addActionListener(deleteListener);

        // ---------------- Delete Course ----------------
        delCourseBtn.addActionListener(ae -> {
            int row = courseTable.getSelectedRow();
            if (row != -1) {
                courses.remove(row);
                courseModel.removeRow(row);

                // Persist deletion
                courseStore.updateFile("courses.dat", courses);
            }
        });

        // ---------------- Add Course ----------------
        addCourseBtn.addActionListener(ae -> {
            JTextField codeField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField creditField = new JTextField();
            JTextField instrNameField = new JTextField();
            JTextField instrQualField = new JTextField();

            Object[] input = {
                    "Course Code:", codeField,
                    "Course Title:", titleField,
                    "Credit Hours:", creditField,
                    "Instructor Name:", instrNameField,
                    "Instructor Qualification:", instrQualField
            };

            int result = JOptionPane.showConfirmDialog(frame, input, "Add Course",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String code = codeField.getText().trim();
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditField.getText().trim());
                    String instrName = instrNameField.getText().trim();
                    String instrQual = instrQualField.getText().trim();

                    if (code.isEmpty() || title.isEmpty() || instrName.isEmpty() || instrQual.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields must be filled!");
                        return;
                    }

                    // Properly create CourseInstructor with name & qualification
                    CourseInstructor instructor = new CourseInstructor(instrName, instrQual);
                    Course newCourse = new Course(code, title, credits, instructor);

                    courses.add(newCourse);
                    courseModel.addRow(new Object[] { newCourse.getCourseCode(), newCourse.getTitle(),
                            newCourse.getCreditHours(), newCourse.getCrsInst().getName() });

                    // Persist the new course
                    courseStore.appendToFile("courses.dat", newCourse);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Credit Hours must be a number!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

    }

    public static void preloadData(ArrayList<Student> students, ArrayList<Course> courses,
            DataStore<Student> studentStore, DataStore<Course> courseStore) {
        CourseInstructor csInstructor = new CourseInstructor("Dr. Ahmed", "PhD");
        CourseInstructor phyInstructor = new CourseInstructor("Prof. Khan", "MSc");
        CourseInstructor mathInstructor = new CourseInstructor("Dr. Sara", "PhD");
        Course cs = new Course("CS101", "Java Programming", 4, csInstructor);
        Course phy = new Course("PHY101", "Physics", 3, phyInstructor);
        Course math = new Course("MATH101", "Calculus", 4, mathInstructor);
        courses.add(cs);
        courses.add(phy);
        courses.add(math);

        Transcript t1 = new Transcript();
        ResultEntry t1_cs = new ResultEntry(cs, 85);
        ResultEntry t1_phy = new ResultEntry(phy, 75);
        ResultEntry t1_math = new ResultEntry(math, 90);
        t1.addResultEntry(t1_cs);
        t1.addResultEntry(t1_phy);
        t1.addResultEntry(t1_math);
        Student s1 = new ScienceStudent("Ali Raza", "Science", t1, "G1");

        Transcript t2 = new Transcript();
        ResultEntry t2_cs = new ResultEntry(cs, 78);
        ResultEntry t2_phy = new ResultEntry(phy, 82);
        ResultEntry t2_math = new ResultEntry(math, 70);
        t2.addResultEntry(t2_cs);
        t2.addResultEntry(t2_phy);
        t2.addResultEntry(t2_math);
        Student s2 = new ArtsStudent("Hassan Ahmed", "Arts", t2, "Painting");

        Transcript t3 = new Transcript();
        ResultEntry t3_cs = new ResultEntry(cs, 65);
        ResultEntry t3_phy = new ResultEntry(phy, 72);
        ResultEntry t3_math = new ResultEntry(math, 68);
        t3.addResultEntry(t3_cs);
        t3.addResultEntry(t3_phy);
        t3.addResultEntry(t3_math);
        Student s3 = new EngineeringStudent("Usman Khalid", "Engineering", t3, "TechCorp");

        Transcript t4 = new Transcript();
        ResultEntry t4_cs = new ResultEntry(cs, 88);
        ResultEntry t4_phy = new ResultEntry(phy, 91);
        ResultEntry t4_math = new ResultEntry(math, 85);
        t4.addResultEntry(t4_cs);
        t4.addResultEntry(t4_phy);
        t4.addResultEntry(t4_math);
        Student s4 = new ScienceStudent("Ahsan Farooq", "Science", t4, "G2");

        Transcript t5 = new Transcript();
        ResultEntry t5_cs = new ResultEntry(cs, 80);
        ResultEntry t5_phy = new ResultEntry(phy, 76);
        ResultEntry t5_math = new ResultEntry(math, 79);
        t5.addResultEntry(t5_cs);
        t5.addResultEntry(t5_phy);
        t5.addResultEntry(t5_math);
        Student s5 = new ArtsStudent("Bilal Tariq", "Arts", t5, "Music");

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);

        studentStore.updateFile("students.dat", students);
        courseStore.updateFile("courses.dat", courses);
    }

}
