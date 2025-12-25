import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class UpdateCourseListener implements ActionListener{
    private JFrame parentFrame;
    private JTable courseTable;
    private DefaultTableModel courseModel;
    private RecordList<Course> courses;
    private DataStore<RecordList<Course>> courseStore;

    public UpdateCourseListener(JFrame parentFrame, JTable courseTable, DefaultTableModel courseModel,
            RecordList<Course> courses, DataStore<RecordList<Course>> courseStore) {
        this.parentFrame = parentFrame;
        this.courseTable = courseTable;
        this.courseModel = courseModel;
        this.courses = courses;
        this.courseStore = courseStore;
    } 

    public void actionPerformed(ActionEvent e){
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Please, Select a Course to Update!");
            return;
        }

        Course c = courses.getItems().get(row);
        String newcrsCode = JOptionPane.showInputDialog(parentFrame, "Enter New Course Code: ", c.getCourseCode());
        if (newcrsCode == null || newcrsCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
            return;
        }

        String newTitle = JOptionPane.showInputDialog(parentFrame, "Enter New Course Title: ", c.getTitle());
        if (newTitle == null || newTitle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
            return;
        }

        String newcrdHrs = JOptionPane.showInputDialog(parentFrame, "Enter New Credit Hours: ", c.getCreditHours());

        try {
            Integer.parseInt(newcrdHrs);
        } catch (NumberFormatException E) {
            JOptionPane.showMessageDialog(parentFrame, "Credit Hour Must Be Numeric!");
        }

        if (newcrdHrs == null || newcrdHrs.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
            return;
        }

        String newInstructor = JOptionPane.showInputDialog(parentFrame, "Enter New Course Instructor: ", c.getCrsInst().getName());
        if (newInstructor == null || newInstructor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
            return;
        }

        String newQual = JOptionPane.showInputDialog(parentFrame, "Enter New Qulaification: ", c.getCrsInst().getQualification());
        if (newQual == null || newQual.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
            return;
        }


        try{
            c.setCourseCode(newcrsCode);
            c.setTitle(newTitle);
            c.setCreditHours(Integer.parseInt(newcrdHrs));
            c.getCrsInst().setName(newInstructor);
            c.getCrsInst().setQualification(newQual);
        }catch(Exception E){
            JOptionPane.showMessageDialog(parentFrame, E.getMessage());
        }

        courseModel.setValueAt(newcrsCode, row, 0);
        courseModel.setValueAt(newTitle, row, 1);
        courseModel.setValueAt(newcrdHrs, row, 2);
        courseModel.setValueAt(newInstructor, row, 3);
        courseModel.setValueAt(newQual, row, 4);

        ArrayList<RecordList<Course>> temp = new ArrayList<>();
        temp.add(courses);
        courseStore.saveToFile("courses.dat", temp);
    }
}
