import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTranscriptListener implements ActionListener {
    private JTable studentTable;
    private RecordList<Student> students;

    public ViewTranscriptListener(JTable studentTable, RecordList<Student> students) {
        this.studentTable = studentTable;
        this.students = students;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a student first.");
            return;
        }

        Student s = students.getItems().get(row);
        if (s == null) {
            JOptionPane.showMessageDialog(null, "Selected student not found.");
            return;
        }

        Transcript t = s.getT();
        if (t == null || t.getResults().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No transcript data available for this student.");
            return;
        }

        String[] cols = {"Course Code", "Course Title", "Marks"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (ResultEntry r : t.getResults()) {
            model.addRow(new Object[] { r.getCourse().getCourseCode(), r.getCourse().getTitle(), r.getMarksObtained() });
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        // Add GPA and Grade at the bottom
        JLabel gpaLabel = new JLabel("GPA: " + String.format("%.2f", s.calculateGPA()) +
                                     " | Grade: " + s.calculateGrade());
        panel.add(gpaLabel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setTitle("Transcript of " + s.getName());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.add(panel);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
