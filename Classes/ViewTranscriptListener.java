import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTranscriptListener implements ActionListener {
    private JFrame parentFrame;
    private JTable studentTable;
    private RecordList<Student> students;

    public ViewTranscriptListener(JFrame parentFrame, JTable studentTable, RecordList<Student> students) {
        this.parentFrame = parentFrame;
        this.studentTable = studentTable;
        this.students = students;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Please select a student first.");
            return;
        }

        Student s = students.getItems().get(row);
        if (s == null) {
            JOptionPane.showMessageDialog(parentFrame, "Selected student not found.");
            return;
        }

        Transcript t = s.getT();
        if (t == null || t.getResults().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No transcript data available for this student.");
            return;
        }

               String[] cols = {"Course Code", "Course Title", "Marks", "GPA", "Grade"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (ResultEntry r : t.getResults()) {
            double courseGPA = t.getCourseGPA(r);
            String courseGrade = t.getCourseGrade(r);

            model.addRow(new Object[] {
                r.getCourse().getCourseCode(),
                r.getCourse().getTitle(),
                r.getMarksObtained(),
                String.format("%.2f", courseGPA),
                courseGrade
});

        }


        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        
        JLabel gpaLabel = new JLabel(
            "CGPA: " + String.format("%.2f", t.getGPA()) +
            " | Grade: " + s.calculateGrade() +
            " | Percentage: " + String.format("%.2f", s.calculatePercentage())
        );


        panel.add(gpaLabel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setTitle("Transcript of " + s.getName());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.add(panel);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
