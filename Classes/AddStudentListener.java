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
        // --- Labels and fields ---
        JLabel nameLabel = new JLabel("Name:");
        JLabel programLabel = new JLabel("Program:");
        JLabel specificLabel = new JLabel();
        JTextField nameField = new JTextField();
        JTextField specificField = new JTextField();
        String[] programs = { "Science", "Arts", "Engineering" };
        JComboBox<String> programBox = new JComboBox<>(programs);
        JButton addResultBtn = new JButton("Add Result Entry");
        JCheckBox feePaidBox = new JCheckBox("Fee Paid");


        // --- Result table ---
        String[] resultCols = { "Course Code", "Course Title", "Marks" };
        DefaultTableModel resultModel = new DefaultTableModel(resultCols, 0);
        JTable resultTable = new JTable(resultModel);
        JScrollPane resultScroll = new JScrollPane(resultTable);

        ArrayList<ResultEntry> tempResults = new ArrayList<>();

        // --- Panel setup ---
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(feePaidBox);
        panel.add(programLabel);
        panel.add(programBox);
        panel.add(specificLabel);
        panel.add(specificField);
        panel.add(addResultBtn);
        panel.add(resultScroll);

        // --- Program selection listener ---
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

        // --- Dialog ---
        JDialog dialog = new JDialog(parentFrame, "Add Student", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.add(panel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Student");
        dialog.add(saveBtn, BorderLayout.SOUTH);

        // --- Add Result Entry button ---
        addResultBtn.addActionListener(ev -> {
            if (courses.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "No courses available!");
                return;
            }


            ArrayList<Course> available = new ArrayList<>();
            for (Course c : courses.getItems()) {  
                boolean alreadyAdded = false;
                for (ResultEntry r : tempResults) {
                    if (r.getCourse().getCourseCode().equals(c.getCourseCode())) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) available.add(c);
            }


            if (available.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "All courses already added!");
                return;
            }

            String[] options = new String[available.size()];
            for (int i = 0; i < available.size(); i++) {
                options[i] = available.get(i).getCourseCode() + " - " + available.get(i).getTitle();
            }

            JComboBox<String> combo = new JComboBox<>(options);
            JTextField marks = new JTextField();
            Object[] input = { "Select Course:", combo, "Marks:", marks };

            int result = JOptionPane.showConfirmDialog(parentFrame, input, "Add Result Entry",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
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

        // --- Save Student button ---
        saveBtn.addActionListener(ev -> {
            if (nameField.getText().trim().isEmpty() || specificField.getText().trim().isEmpty()
                    || tempResults.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Fill all fields & add at least one result!");
                return;
            }

            String program = (String) programBox.getSelectedItem();
            boolean feePaid = feePaidBox.isSelected();
            Transcript t = new Transcript();
            t.setResults(tempResults);

            Student s;
            if (program.equalsIgnoreCase("Science"))
                s = new ScienceStudent(nameField.getText().trim(), program, t, feePaid, specificField.getText().trim());
            else if (program.equalsIgnoreCase("Arts"))
                s = new ArtsStudent(nameField.getText().trim(), program, t, feePaid, specificField.getText().trim());
            else
                s = new EngineeringStudent(nameField.getText().trim(), program, t, feePaid, specificField.getText().trim());

            students.addItem(s);
            if (feePaid) {
                studentModel.addRow(new Object[] { s.getStudentId(), s.getName(), s.getProgram(),
                    String.format("%.2f", s.calculateGPA()), s.calculateGrade() });    
            }else{
                studentModel.addRow(new Object[] { s.getStudentId(), s.getName(), s.getProgram(), "Fee Pending", "---" });
            }
            

            // --- Wrap RecordList in ArrayList and save ---
            ArrayList<RecordList<Student>> wrapper = new ArrayList<>();
            wrapper.add(students);
            studentStore.saveToFile("students.dat", wrapper);

            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}
