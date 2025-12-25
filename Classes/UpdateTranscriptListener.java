import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdateTranscriptListener implements ActionListener{
    private JFrame parentFrame;
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private RecordList<Student> students;
    private DataStore<RecordList<Student>> studentStore;

    public UpdateTranscriptListener(JFrame parentFrame, JTable studentTable, DefaultTableModel studentModel,
        RecordList<Student> students, DataStore<RecordList<Student>> studentStore) {
        this.parentFrame = parentFrame;
        this.studentTable = studentTable;
        this.studentModel = studentModel;
        this.students = students;
        this.studentStore = studentStore;
    }   

    public void actionPerformed(ActionEvent e){
        int row = studentTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Please select a student first.");
            return;
        }

        Student s = students.getItems().get(row);
        Transcript t = s.getT();
        
        for(ResultEntry r : t.getResults()){
            String newMarks = JOptionPane.showInputDialog(parentFrame, "Enter Obtain Marks for Course: " + r.getCourse().getTitle() , r.getMarksObtained());
            if (newMarks == null || newMarks.trim().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Field Cannot Be Empty!");
                return;
            }
            try {
                r.setMarksObtained(Double.parseDouble(newMarks));
            } catch (NumberFormatException E) {
                JOptionPane.showMessageDialog(parentFrame, "Credit Hour Must Be Numeric!");
            }catch(Exception E){
                JOptionPane.showMessageDialog(parentFrame, E.getMessage());
            }
            
        }

        studentModel.setValueAt(String.format("%.2f", s.calculateGPA()), row, 3);
        studentModel.setValueAt(s.calculateGrade(), row, 4);

        ArrayList<RecordList<Student>> temp = new ArrayList<>();
        temp.add(students);
        studentStore.saveToFile("students.dat", temp);
        JOptionPane.showMessageDialog(parentFrame, "Transcipt Updated Successfully!");
    }
}
