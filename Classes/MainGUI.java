import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI {

    public static void main(String[] args) {

        DataStore<Student> studentStore = new DataStore<>();
        DataStore<Course> courseStore = new DataStore<>();

        ArrayList<Student> students = studentStore.loadFromFile("students.dat");
        ArrayList<Course> courses = courseStore.loadFromFile("courses.dat");
        if (students.isEmpty()) {
            preloadData(students, courses, studentStore, courseStore);
        }
//YAHAN TK DONE
        JFrame frame = new JFrame("Student Result Management System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabs = new JTabbedPane();

        // ---------------- Students Tab ----------------
        String[] studentCols = { "ID", "Name", "Program", "GPA", "Grade" };
        DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0);
        JTable studentTable = new JTable(studentModel);

        for (Student s : students)
            studentModel.addRow(new Object[] { s.getStudentId(), s.getName(), s.getProgram(),
                    String.format("%.2f", s.calculateGPA()), s.calculateGrade() });

        JButton addStudentBtn = new JButton("Add Student");
        JButton delStudentBtn = new JButton("Delete Student");

        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        JPanel sBtns = new JPanel();
        sBtns.add(addStudentBtn);
        sBtns.add(delStudentBtn);
        studentPanel.add(sBtns, BorderLayout.SOUTH);
        tabs.addTab("Students", studentPanel);

        // ---------------- Courses Tab ----------------
        String[] courseCols = { "Code", "Title", "Credits", "Instructor" };
        DefaultTableModel courseModel = new DefaultTableModel(courseCols, 0);
        JTable courseTable = new JTable(courseModel);

        for (Course c : courses)
            courseModel.addRow(
                    new Object[] { c.getCourseCode(), c.getTitle(), c.getCreditHours(), c.getCrsInst().getName() });

        JButton addCourseBtn = new JButton("Add Course");
        JButton delCourseBtn = new JButton("Delete Course");

        JPanel coursePanel = new JPanel(new BorderLayout());
        coursePanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        JPanel cBtns = new JPanel();
        cBtns.add(addCourseBtn);
        cBtns.add(delCourseBtn);
        coursePanel.add(cBtns, BorderLayout.SOUTH);
        tabs.addTab("Courses", coursePanel);

        frame.add(tabs);
        frame.setVisible(true);

        // ---------------- Add Student ----------------
        addStudentBtn.addActionListener(ae -> {
            JTextField nameField = new JTextField();
            String[] programs = { "Science", "Arts", "Engineering" };
            JComboBox<String> programBox = new JComboBox<>(programs);
            JTextField specificField = new JTextField();
            JLabel specificLabel = new JLabel();
            String[] resultCols = { "Course Code", "Course Title", "Marks" };
            DefaultTableModel resultModel = new DefaultTableModel(resultCols, 0);
            JTable resultTable = new JTable(resultModel);
            JScrollPane resultScroll = new JScrollPane(resultTable);
            ArrayList<ResultEntry> tempResults = new ArrayList<>();
            JButton addResultBtn = new JButton("Add Result Entry");

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Program:"));
            panel.add(programBox);
            panel.add(specificLabel);
            panel.add(specificField);
            panel.add(addResultBtn);
            panel.add(resultScroll);

            programBox.addActionListener(ev -> {
                String program = (String) programBox.getSelectedItem();
                if (program.equalsIgnoreCase("Science"))
                    specificLabel.setText("Lab Group:");
                else if (program.equalsIgnoreCase("Arts"))
                    specificLabel.setText("Major Art Form:");
                else
                    specificLabel.setText("Internship Company:");
                specificField.setText("");
            });
            programBox.setSelectedIndex(0);

            JDialog dialog = new JDialog(frame, "Add Student", true);
            dialog.setSize(500, 450);
            dialog.setLocationRelativeTo(frame);
            dialog.add(panel, BorderLayout.CENTER);
            JButton saveBtn = new JButton("Save Student");
            dialog.add(saveBtn, BorderLayout.SOUTH);

            addResultBtn.addActionListener(ev -> {
                if (courses.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No courses!");
                    return;
                }
                ArrayList<Course> available = new ArrayList<>();
                for (Course c : courses) {
                    boolean already = tempResults.stream()
                            .anyMatch(r -> r.getCourse().getCourseCode().equals(c.getCourseCode()));
                    if (!already)
                        available.add(c);
                }
                if (available.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All courses added!");
                    return;
                }

                String[] options = new String[available.size()];
                for (int i = 0; i < available.size(); i++)
                    options[i] = available.get(i).getCourseCode() + " - " + available.get(i).getTitle();

                JComboBox<String> combo = new JComboBox<>(options);
                JTextField marks = new JTextField();
                Object[] input = { "Select Course:", combo, "Marks:", marks };

                if (JOptionPane.showConfirmDialog(frame, input, "Add Result Entry",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
                    try {
                        int idx = combo.getSelectedIndex();
                        Course selected = available.get(idx);
                        double m = Double.parseDouble(marks.getText().trim());
                        tempResults.add(new ResultEntry(selected, m));
                        resultModel.addRow(new Object[] { selected.getCourseCode(), selected.getTitle(), m });
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Invalid input!");
                    }
                }
            });

            saveBtn.addActionListener(ev -> {
                if (nameField.getText().trim().isEmpty() || specificField.getText().trim().isEmpty()
                        || tempResults.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Fill all fields & add at least one result!");
                    return;
                }

                String program = (String) programBox.getSelectedItem();
                Transcript t = new Transcript();
                t.setResults(tempResults);
                Student s;
                if (program.equalsIgnoreCase("Science"))
                    s = new ScienceStudent(nameField.getText().trim(), program, t, specificField.getText().trim());
                else if (program.equalsIgnoreCase("Arts"))
                    s = new ArtsStudent(nameField.getText().trim(), program, t, specificField.getText().trim());
                else
                    s = new EngineeringStudent(nameField.getText().trim(), program, t, specificField.getText().trim());

                students.add(s);
                studentModel.addRow(new Object[] { s.getStudentId(), s.getName(), s.getProgram(),
                        String.format("%.2f", s.calculateGPA()), s.calculateGrade() });

                // Persist new student
                studentStore.appendToFile("students.dat", s);

                dialog.dispose();
            });

            dialog.setVisible(true);
        });

        // ---------------- Delete Student ----------------
        delStudentBtn.addActionListener(ae -> {
            int row = studentTable.getSelectedRow();
            if (row != -1) {
                students.remove(row);
                studentModel.removeRow(row);

                // Persist deletion
                studentStore.updateFile("students.dat", students);
            }
        });

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

    public static void preloadData(ArrayList<Student> students, ArrayList<Course> courses,DataStore<Student> studentStore, DataStore<Course> courseStore) {
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
