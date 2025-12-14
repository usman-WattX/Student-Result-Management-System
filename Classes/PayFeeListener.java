import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class PayFeeListener implements ActionListener {
    private JTable table;
    private DefaultTableModel model;
    private RecordList<Student> students;
    private DataStore<RecordList<Student>> studentStore;

    public PayFeeListener(JTable table, DefaultTableModel model, RecordList<Student> students,
                          DataStore<RecordList<Student>> store) {
        this.table = table;
        this.model = model;
        this.students = students;
        this.studentStore = store;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a student!", "Pay Fee", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        Student s = students.getItems().get(row);
        if (s.isFeePaid()) {
            JOptionPane.showMessageDialog(null, "Fee already paid!", "Pay Fee", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Confirm fee payment for " + s.getName() + "?",
                "Pay Fee", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            s.setFeePaid(true);
            model.setValueAt(String.format("%.2f", s.calculateGPA()), row, 3);
            model.setValueAt(s.calculateGrade(), row, 4);

            ArrayList<RecordList<Student>> wrapper = new ArrayList<>();
            wrapper.add(students);
            studentStore.saveToFile("students.dat", wrapper);

            JOptionPane.showMessageDialog(null, "Fee paid successfully!", "Pay Fee", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
