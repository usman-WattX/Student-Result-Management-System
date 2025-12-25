import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class UpdateStudentListener implements ActionListener{
    private JFrame parentFrame;
    private JTable studenTable;
    private DefaultTableModel studentModel;
    private RecordList<Student> students;
    private DataStore<RecordList<Student>> studentStore;

    public UpdateStudentListener(JFrame parentFrame, JTable studentTable,DefaultTableModel studentModel,
        RecordList<Student> students, DataStore<RecordList<Student>> studentStore) {
        this.parentFrame = parentFrame;
        this.studenTable = studentTable;
        this.studentModel = studentModel;
        this.students = students;
        this.studentStore = studentStore;
    }

    public void actionPerformed(ActionEvent e){

        int row = studenTable.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(parentFrame, "Please, Select a Student to Update!");
            return;
        }

        Student s = students.getItems().get(row);
        String newName = JOptionPane.showInputDialog(parentFrame, "Enter New Name: ", s.getName());
        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot be Empty!");
            return;
        }

        String newProgram = JOptionPane.showInputDialog(parentFrame, "Enter New Program: ", s.getProgram());
        if (newProgram == null || newProgram.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Field Cannot be Empty!");
            return;
        }

        try{
            s.setName(newName);
            s.setProgram(newProgram);
        }catch (Exception E){
            JOptionPane.showMessageDialog(parentFrame, E.getMessage());
            return;
        }

        studentModel.setValueAt(s.getName(), row, 1);
        studentModel.setValueAt(s.getProgram(), row, 2);

        ArrayList<RecordList<Student>> temp = new ArrayList<>();
        temp.add(students);
        studentStore.saveToFile("students.dat", temp);
        JOptionPane.showMessageDialog(parentFrame, "Student Updated Successfully!");

    }
    
}
