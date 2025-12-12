import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddCourseListener implements ActionListener {
    private JFrame parentFrame;
    private DefaultTableModel courseModel;
    private RecordList<Course> courses;
    private DataStore<RecordList<Course>> courseStore;

    public AddCourseListener(JFrame parentFrame,
                             DefaultTableModel courseModel,
                             RecordList<Course> courses,
                             DataStore<RecordList<Course>> courseStore) {

        this.parentFrame = parentFrame;
        this.courseModel = courseModel;
        this.courses = courses;
        this.courseStore = courseStore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JTextField codeField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField creditField = new JTextField();
        JTextField instrNameField = new JTextField();
        JTextField instrQualField = new JTextField();

        Object[] input = {
                "Course Code:", codeField,
                "Course Title:", titleField,
                "Credit Hours:", creditField,
                "Instructor Name:", instrNameField,
                "Instructor Qualification:", instrQualField
        };

        int result = JOptionPane.showConfirmDialog(
                parentFrame,
                input,
                "Add Course",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            String code = codeField.getText().trim();
            String title = titleField.getText().trim();
            String creditText = creditField.getText().trim();
            String instrName = instrNameField.getText().trim();
            String instrQual = instrQualField.getText().trim();

            if (code.isEmpty() || title.isEmpty() ||
                creditText.isEmpty() || instrName.isEmpty() || instrQual.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "All fields must be filled!");
                return;
            }

            int credits;
            try {
                credits = Integer.parseInt(creditText);
                if (credits <= 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Credit Hours must be a positive number!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Credit Hours must be numeric!");
                return;
            }

            CourseInstructor instructor = new CourseInstructor(instrName, instrQual);
            Course newCourse = new Course(code, title, credits, instructor);

            courses.addItem(newCourse);

            courseModel.addRow(new Object[]{
                    newCourse.getCourseCode(),
                    newCourse.getTitle(),
                    newCourse.getCreditHours(),
                    newCourse.getCrsInst().getName()
            });

            try {
                ArrayList<RecordList<Course>> temp = new ArrayList<>();
                temp.add(courses);
                courseStore.saveToFile("courses.dat", temp);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Error saving course file!");
            }

            JOptionPane.showMessageDialog(parentFrame, "Course Added Successfully!");
        }
    }
}
