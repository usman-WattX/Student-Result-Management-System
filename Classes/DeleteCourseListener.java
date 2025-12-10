import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCourseListener implements ActionListener {
    private JTable courseTable;
    private DefaultTableModel courseModel;
    private ArrayList<Course> courses;
    private DataStore<Course> courseStore;

    public DeleteCourseListener(JTable courseTable, DefaultTableModel courseModel,
                                ArrayList<Course> courses, DataStore<Course> courseStore) {
        this.courseTable = courseTable;
        this.courseModel = courseModel;
        this.courses = courses;
        this.courseStore = courseStore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a course to delete.");
            return;
        }

        if (row >= 0 && row < courses.size()) {
            courses.remove(row);
            courseModel.removeRow(row);

            try {
                courseStore.updateFile("courses.dat", courses);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selected row is invalid.");
        }
    }
}
