import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
public class DeleteStudentListener implements ActionListener {
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private ArrayList<Student> students;
    private DataStore<Student> studentStore;

    public DeleteStudentListener(JTable studentTable, DefaultTableModel studentModel, ArrayList<Student> students, DataStore<Student> studentStore) {
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

        if (row >= 0 && row < students.size()) {
            students.remove(row);
            studentModel.removeRow(row);

            try {
                studentStore.updateFile("students.dat", students);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selected row is invalid.");
        }
    }
}