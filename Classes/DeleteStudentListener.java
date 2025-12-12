import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteStudentListener implements ActionListener {
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private RecordList<Student> students;
    private DataStore<RecordList<Student>> studentStore;

    public DeleteStudentListener(JTable studentTable, DefaultTableModel studentModel,
                                 RecordList<Student> students, DataStore<RecordList<Student>> studentStore) {
        this.studentTable = studentTable;
        this.studentModel = studentModel;
        this.students = students;
        this.studentStore = studentStore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a student to delete.");
            return;
        }

        if (row >= 0 && row < students.getItems().size()) {
            Student s = students.getItems().get(row);
            students.removeItem(s.getStudentId());
            studentModel.removeRow(row);

            try {
                ArrayList<RecordList<Student>> temp = new ArrayList<>();
                temp.add(students);
                studentStore.saveToFile("students.dat", temp);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selected row is invalid.");
        }
    }
}
