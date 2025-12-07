import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // --- Data ---
        List<Object[]> students = new ArrayList<>();
        List<Object[]> courses = new ArrayList<>();
        // Preloaded Students: {ID, Name, Program, GPA, Grade}
        students.add(new Object[]{"SCI101", "Ali Khan", "Science", 3.8, "A"});
        students.add(new Object[]{"SCI102", "Ayesha Noor", "Science", 3.5, "A-"});
        students.add(new Object[]{"ART101", "Omar Farooq", "Arts", 3.2, "B+"});
        students.add(new Object[]{"ENG101", "Zara Ahmed", "Engineering", 3.7, "A"});
        students.add(new Object[]{"ENG102", "Sara Khan", "Engineering", 3.0, "B"});
        // Preloaded Courses: {Code, Title, Credits, Instructor}
        courses.add(new Object[]{"CS101", "Java Programming", 4, "Dr. Ahmed"});
        courses.add(new Object[]{"PHY101", "Physics", 3, "Prof. Khan"});
        courses.add(new Object[]{"MATH101", "Calculus", 4, "Dr. Sara"});
        // --- Frame ---
        JFrame frame = new JFrame("Student Result Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // --- Tabs ---
        JTabbedPane tabs = new JTabbedPane();
        // ----------------- Students Tab -----------------
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new java.awt.BorderLayout());
        // Table
        String[] studentCols = {"ID", "Name", "Program", "GPA", "Grade"};
        DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0);
        JTable studentTable = new JTable(studentModel);
        JScrollPane studentScroll = new JScrollPane(studentTable);
        studentPanel.add(studentScroll, java.awt.BorderLayout.CENTER);
        // Buttons
        JPanel studentBtnPanel = new JPanel();
        studentBtnPanel.setLayout(new java.awt.FlowLayout());
        JButton addStudent = new JButton("Add Student");
        JButton deleteStudent = new JButton("Delete Student");
        studentBtnPanel.add(addStudent);
        studentBtnPanel.add(deleteStudent);
        studentPanel.add(studentBtnPanel, java.awt.BorderLayout.SOUTH);
        // Load initial students
        for (Object[] s : students) studentModel.addRow(s);
        // Add Student Action
        addStudent.addActionListener(ae -> {
            JTextField nameField = new JTextField();
            JTextField gpaField = new JTextField();
            JTextField gradeField = new JTextField();
            String[] programs = {"Science","Arts","Engineering"};
            JComboBox<String> programBox = new JComboBox<>(programs);
            Object[] inputs = {
                "Student Name:", nameField,
                "Program:", programBox,
                "GPA:", gpaField,
                "Grade:", gradeField
            };
            int result = JOptionPane.showConfirmDialog(frame, inputs, "Add New Student",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result != JOptionPane.OK_OPTION) return;
            String name = nameField.getText().trim();
            if(name.isEmpty()) return;
            String program = (String) programBox.getSelectedItem();
            double gpa = 0.0;
            try { gpa = Double.parseDouble(gpaField.getText().trim()); } catch(Exception ex) {}
            String grade = gradeField.getText().trim();
            if(grade.isEmpty()) grade = "-";
            String id = program.substring(0,3).toUpperCase() + (students.size()+101);
            Object[] row = {id, name, program, gpa, grade};
            students.add(row);
            studentModel.addRow(row);
        });
        // Delete Student Action
        deleteStudent.addActionListener(ae -> {
            int row = studentTable.getSelectedRow();
            if(row != -1) {
                students.remove(row);
                studentModel.removeRow(row);
            }
        });
        tabs.addTab("Students", studentPanel);
        // ----------------- Courses Tab -----------------
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new java.awt.BorderLayout());
        String[] courseCols = {"Code", "Title", "Credits", "Instructor"};
        DefaultTableModel courseModel = new DefaultTableModel(courseCols,0);
        JTable courseTable = new JTable(courseModel);
        JScrollPane courseScroll = new JScrollPane(courseTable);
        coursePanel.add(courseScroll, java.awt.BorderLayout.CENTER);
        JButton addCourse = new JButton("Add Course");
        JPanel courseBtnPanel = new JPanel();
        courseBtnPanel.add(addCourse);
        coursePanel.add(courseBtnPanel, java.awt.BorderLayout.SOUTH);
        // Load initial courses
        for (Object[] c : courses) courseModel.addRow(c);
        // Add Course Action
        addCourse.addActionListener(ae -> {
            JTextField codeField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField creditsField = new JTextField();
            JTextField instrField = new JTextField();
            Object[] inputs = {
                "Course Code:", codeField,
                "Title:", titleField,
                "Credits:", creditsField,
                "Instructor:", instrField
            };
            int result = JOptionPane.showConfirmDialog(frame, inputs, "Add New Course",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result != JOptionPane.OK_OPTION) return;
            String code = codeField.getText().trim();
            String title = titleField.getText().trim();
            int credits = 0;
            try { credits = Integer.parseInt(creditsField.getText().trim()); } catch(Exception ex) {}
            String instr = instrField.getText().trim();
            Object[] row = {code, title, credits, instr};
            courses.add(row);
            courseModel.addRow(row);
        });
        tabs.addTab("Courses", coursePanel);
        // ----------------- Show Frame -----------------
        frame.add(tabs, java.awt.BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
