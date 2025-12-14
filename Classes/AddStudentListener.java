import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddStudentListener implements ActionListener {
    private JFrame parentFrame;
    private DefaultTableModel studentModel;
    private RecordList<Student> students;
    private DataStore<RecordList<Student>> studentStore;
    private RecordList<Course> courses;

    public AddStudentListener(JFrame frame, DefaultTableModel model, RecordList<Student> students,
            DataStore<RecordList<Student>> store, RecordList<Course> courses) {
        this.parentFrame = frame;
        this.studentModel = model;
        this.students = students;
        this.studentStore = store;
        this.courses = courses;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField nameField = new JTextField();
        JComboBox<String> programBox = new JComboBox<>();
        programBox.addItem("Science");
        programBox.addItem("Arts");
        programBox.addItem("Engineering");
        JLabel specificLabel = new JLabel("Specific:");
        JTextField specificField = new JTextField();
        DefaultTableModel resultModel = new DefaultTableModel();
        resultModel.addColumn("Course");
        resultModel.addColumn("Marks");
        JTable resultTable = new JTable(resultModel);
        ArrayList<ResultEntry> results = new ArrayList<>();
        JButton addResultBtn = new JButton("Add Result");
        JButton saveBtn = new JButton("Save Student");
        JCheckBox feepaidBox = new JCheckBox("Fee Paid");
        JScrollPane scrollPane = new JScrollPane(resultTable);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(feepaidBox);
        panel.add(new JLabel("Program:"));
        panel.add(programBox);
        panel.add(specificLabel);
        panel.add(specificField);
        panel.add(addResultBtn);
        panel.add(scrollPane);
        panel.add(saveBtn);
        JDialog dialog = new JDialog(parentFrame, "Add Student", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.add(panel, BorderLayout.CENTER);
        programBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String program = (String) programBox.getSelectedItem();
                if (program.equals("Science")) {
                    specificLabel.setText("Lab Group:");
                } else if (program.equals("Arts")) {
                    specificLabel.setText("Major Art:");
                } else {
                    specificLabel.setText("Internship:");
                }
            }
        });
        programBox.setSelectedIndex(0);
        addResultBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (courses.getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "No courses available!");
                    return;
                }
                ArrayList<Course> available = new ArrayList<>();
                for (Course c : courses.getItems()) {
                    boolean already = false;
                    for (ResultEntry r : results)
                        if (r.getCourse().getCourseCode().equals(c.getCourseCode()))
                            already = true;
                    if (!already)
                        available.add(c);
                }
                if (available.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "All courses added!");
                    return;
                }
                String[] options = new String[available.size()];
                for (int i = 0; i < available.size(); i++)
                    options[i] = available.get(i).getCourseCode() + " - " + available.get(i).getTitle();
                JComboBox<String> combo = new JComboBox<>(options);
                JTextField marksField = new JTextField();
                Object[] input = new Object[4];
                input[0] = "Select Course:";
                input[1] = combo;
                input[2] = "Marks:";
                input[3] = marksField;
                int res = JOptionPane.showConfirmDialog(parentFrame, input, "Add Result", JOptionPane.OK_CANCEL_OPTION);
                if (res == JOptionPane.OK_OPTION) {
                    try {
                        Course selectedCourse = available.get(combo.getSelectedIndex());
                        double marks = Double.parseDouble(marksField.getText().trim());
                        ResultEntry entry = new ResultEntry(selectedCourse, marks);
                        results.add(entry);
                        resultModel.addRow(new Object[] { selectedCourse.getCourseCode(), marks });
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parentFrame, "Invalid input!");
                    }
                }
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().trim().isEmpty() || specificField.getText().trim().isEmpty()
                        || results.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Fill all fields and add at least one result!");
                    return;
                }
                Transcript t = new Transcript();
                t.setResults(results);
                String program = (String) programBox.getSelectedItem();
                boolean feeStatus = feepaidBox.isSelected();
                Student s = null;
                if (program.equals("Science")) {
                    s = new ScienceStudent(nameField.getText().trim(), program, t, feeStatus,specificField.getText().trim());
                } else if (program.equals("Arts")) {
                    s = new ArtsStudent(nameField.getText().trim(), program, t, feeStatus,specificField.getText().trim());
                } else {
                    s = new EngineeringStudent(nameField.getText().trim(), program, t, feeStatus,specificField.getText().trim());
                }
                students.addItem(s);
                studentModel.addRow(new Object[] { s.getStudentId(), s.getName(), s.getProgram(),
                        String.format("%.2f", s.calculateGPA()), s.calculateGrade() });
                ArrayList<RecordList<Student>> wrapper = new ArrayList<>();
                wrapper.add(students);
                studentStore.saveToFile("students.dat", wrapper);
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }
}