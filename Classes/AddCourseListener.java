import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddCourseListener implements ActionListener {
    private JFrame parentFrame;
    private DefaultTableModel courseModel;
    private ArrayList<Course> courses;
    private DataStore<Course> courseStore;

    public AddCourseListener(JFrame parentFrame,
                             DefaultTableModel courseModel,
                             ArrayList<Course> courses,
                             DataStore<Course> courseStore) {

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

            // Validate empty fields
            if (code.isEmpty() || title.isEmpty() ||
                creditText.isEmpty() || instrName.isEmpty() || instrQual.isEmpty()) {

                JOptionPane.showMessageDialog(parentFrame, "All fields must be filled!");
                return;
            }

            // Validate credit hours
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

            // Create objects
            CourseInstructor instructor = new CourseInstructor(instrName, instrQual);
            Course newCourse = new Course(code, title, credits, instructor);

            // Add to list
            courses.add(newCourse);

            // Add to table
            courseModel.addRow(new Object[]{
                    newCourse.getCourseCode(),
                    newCourse.getTitle(),
                    newCourse.getCreditHours(),
                    newCourse.getCrsInst().getName()
            });

            // Save to file
            courseStore.updateFile("courses.dat", courses);

            JOptionPane.showMessageDialog(parentFrame, "Course Added Successfully!");
        }
    }
}
