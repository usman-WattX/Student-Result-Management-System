import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCourseListener implements ActionListener {
    private JTable courseTable;
    private DefaultTableModel courseModel;
    private RecordList<Course> courses;
    private DataStore<RecordList<Course>> courseStore;
    private JLabel totalCrsLbl;

    public DeleteCourseListener(JTable courseTable, DefaultTableModel courseModel, RecordList<Course> 
        courses, DataStore<RecordList<Course>> courseStore, JLabel totalCrsLbl) {
        this.courseTable = courseTable;
        this.courseModel = courseModel;
        this.courses = courses;
        this.courseStore = courseStore;
        this.totalCrsLbl = totalCrsLbl;
        this.totalCrsLbl.setText("Total Courses: " + Course.getTotalCourses());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a course to delete.");
            return;
        }

        if (row >= 0 && row < courses.getItems().size()) {
            Course c = courses.getItems().get(row);
            courses.removeItem(c.getCourseCode());
            courseModel.removeRow(row);
            Course.decrementCourseCounter();
            totalCrsLbl.setText("Total Courses: " + Course.getTotalCourses());

            try {
                ArrayList<RecordList<Course>> temp = new ArrayList<>();
                temp.add(courses);
                courseStore.saveToFile("courses.dat", temp);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selected row is invalid.");
        }
    }
}
