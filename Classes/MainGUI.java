import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGUI {

    private static final Color APP_BG = new Color(0xF6F8FC);
    private static final Color SURFACE = Color.WHITE;
    private static final Color SURFACE_2 = new Color(0xF1F5F9);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(0x1F2937);
    private static final Color MUTED = new Color(0x64748B);
    private static final Color BORDER = new Color(0xD7DFEA);
    private static final Color ACCENT = new Color(0x2563EB);
    private static final Color ACCENT_HOVER = new Color(0x1D4ED8);
    private static final Color ACCENT_PRESSED = new Color(0x1E40AF);
    private static final Color SUCCESS = new Color(0x16A34A);
    private static final Color SUCCESS_HOVER = new Color(0x15803D);
    private static final Color SUCCESS_PRESSED = new Color(0x166534);
    private static final Color DANGER = new Color(0xDC2626);
    private static final Color DANGER_HOVER = new Color(0xB91C1C);
    private static final Color DANGER_PRESSED = new Color(0x991B1B);
    private static final Color CHIP_BG = new Color(0xEEF3F9);
    private static final Color TABLE_ALT = new Color(0xFAFCFF);
    private static final Color TABLE_SELECTION = new Color(0xDBEAFE);
    private static final Color DISABLED_BG = new Color(0xE2E8F0);
    private static final Color DISABLED_TEXT = new Color(0x94A3B8);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            applyTheme();

            DataStore<RecordList<Student>> studentStore = new DataStore<>();
            DataStore<RecordList<Course>> courseStore = new DataStore<>();

            ArrayList<RecordList<Student>> loadedStudents = studentStore.loadFromFile("students.dat");
            ArrayList<RecordList<Course>> loadedCourses = courseStore.loadFromFile("courses.dat");

            RecordList<Student> students = loadedStudents.isEmpty() ? new RecordList<>() : loadedStudents.get(0);
            RecordList<Course> courses = loadedCourses.isEmpty() ? new RecordList<>() : loadedCourses.get(0);

            if (!students.getItems().isEmpty()) {
                Student lastS = students.getItems().get(students.getItems().size() - 1);
                String num = lastS.getStudentId().substring(1);
                Student.setStudentCounter(Integer.parseInt(num));
            }

            if (!loadedCourses.isEmpty()) {
                Course.initilaCourseCount(courses.getItems().size());
            }

            if (students.getItems().isEmpty()) {
                preloadData(students, courses, studentStore, courseStore);
            }

            JFrame frame = new JFrame("Student Result Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 760);
            frame.setMinimumSize(new Dimension(1160, 680));
            frame.setLocationRelativeTo(null);
            frame.setBackground(APP_BG);

            JPanel root = new JPanel(new BorderLayout(16, 16));
            root.setBackground(APP_BG);
            root.setBorder(new EmptyBorder(16, 16, 16, 16));
            frame.setContentPane(root);

            root.add(buildTopBar(students, courses), BorderLayout.NORTH);

            JTabbedPane tabs = buildTabs();
            tabs.addTab("Students", buildStudentPanel(frame, students, studentStore, courses));
            tabs.addTab("Courses", buildCoursePanel(frame, courses, courseStore));
            root.add(tabs, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private static void applyTheme() {
        UIManager.put("Panel.background", APP_BG);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));

        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", ACCENT);
        UIManager.put("Button.focusPainted", false);

        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextField.background", SURFACE);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", TEXT);
        UIManager.put("TextField.border", new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)));

        UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextArea.background", SURFACE);
        UIManager.put("TextArea.foreground", TEXT);

        UIManager.put("OptionPane.background", SURFACE);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("OptionPane.foreground", TEXT);

        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Table.background", SURFACE);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.gridColor", BORDER);
        UIManager.put("Table.selectionBackground", TABLE_SELECTION);
        UIManager.put("Table.selectionForeground", TEXT);

        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("TableHeader.background", SURFACE_2);
        UIManager.put("TableHeader.foreground", TEXT);

        UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("TabbedPane.background", APP_BG);
        UIManager.put("TabbedPane.foreground", TEXT);

        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("ScrollBar.width", 10);
    }

    private static JPanel buildTopBar(RecordList<Student> students, RecordList<Course> courses) {
        JPanel bar = new JPanel(new BorderLayout(16, 16));
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(4, 4, 4, 4));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Student Result Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT);

        left.add(title);
        left.add(Box.createVerticalStrut(4));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(infoChip("Students", String.valueOf(students.getItems().size()), ACCENT));
        right.add(infoChip("Courses", String.valueOf(courses.getItems().size()), SUCCESS));

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private static JPanel infoChip(String label, String value, Color accent) {
        JPanel chip = new JPanel();
        chip.setBackground(CHIP_BG);
        chip.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(10, 14, 10, 14)));
        chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);

        JPanel dot = new JPanel();
        dot.setPreferredSize(new Dimension(9, 9));
        dot.setBackground(accent);
        dot.setBorder(new EmptyBorder(0, 0, 0, 0));
        top.add(dot);

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 18));
        v.setForeground(TEXT);

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(MUTED);

        chip.add(top);
        chip.add(Box.createVerticalStrut(8));
        chip.add(v);
        chip.add(Box.createVerticalStrut(3));
        chip.add(l);
        return chip;
    }

    private static JTabbedPane buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(APP_BG);
        tabs.setForeground(TEXT);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBorder(null);
        return tabs;
    }

    private static JPanel buildStudentPanel(JFrame frame,
            RecordList<Student> students,
            DataStore<RecordList<Student>> studentStore,
            RecordList<Course> courses) {

        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(APP_BG);
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));

        panel.add(sectionHeader("Students", ""),
                BorderLayout.NORTH);

        String[] cols = { "ID", "Name", "Program", "GPA", "Grade" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = modernTable(model);
        refreshStudentTable(model, students);

        panel.add(modernScroll(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(2, 0, 0, 0));

        JButton addBtn = new ThemeButton("Add Student", ACCENT, ACCENT_HOVER, ACCENT_PRESSED);
        JButton updateBtn = new ThemeButton("Update Student", SURFACE, SURFACE_2, SURFACE_2);
        JButton transcriptBtn = new ThemeButton("View Transcript", SURFACE, SURFACE_2, SURFACE_2);
        JButton updateTranscriptBtn = new ThemeButton("Update Transcript", SURFACE, SURFACE_2, SURFACE_2);
        JButton deleteBtn = new ThemeButton("Delete Student", DANGER, DANGER_HOVER, DANGER_PRESSED);
        JButton feeBtn = new ThemeButton("Pay Fee", SUCCESS, SUCCESS_HOVER, SUCCESS_PRESSED);

        updateBtn.setForeground(TEXT);
        transcriptBtn.setForeground(TEXT);
        updateTranscriptBtn.setForeground(TEXT);

        actions.add(addBtn);
        actions.add(updateBtn);
        actions.add(transcriptBtn);
        actions.add(updateTranscriptBtn);
        actions.add(deleteBtn);
        actions.add(feeBtn);

        panel.add(actions, BorderLayout.SOUTH);

        AddStudentListener addListener = new AddStudentListener(frame, model, students, studentStore, courses);
        UpdateStudentListener updateListener = new UpdateStudentListener(frame, table, model, students, studentStore);
        DeleteStudentListener deleteListener = new DeleteStudentListener(frame, table, model, students, studentStore);
        ViewTranscriptListener viewListener = new ViewTranscriptListener(frame, table, students);
        UpdateTranscriptListener transcriptListener = new UpdateTranscriptListener(frame, table, model, students,
                studentStore);
        PayFeeListener payFeeListener = new PayFeeListener(frame, table, model, students, studentStore);

        addBtn.addActionListener(e -> {
            addListener.actionPerformed(e);
            refreshStudentTable(model, students);
        });
        updateBtn.addActionListener(e -> {
            updateListener.actionPerformed(e);
            refreshStudentTable(model, students);
        });
        transcriptBtn.addActionListener(e -> viewListener.actionPerformed(e));
        updateTranscriptBtn.addActionListener(e -> {
            transcriptListener.actionPerformed(e);
            refreshStudentTable(model, students);
        });
        deleteBtn.addActionListener(e -> {
            deleteListener.actionPerformed(e);
            refreshStudentTable(model, students);
        });
        feeBtn.addActionListener(e -> {
            payFeeListener.actionPerformed(e);
            refreshStudentTable(model, students);
        });

        return panel;
    }

    private static JPanel buildCoursePanel(JFrame frame,
            RecordList<Course> courses,
            DataStore<RecordList<Course>> courseStore) {

        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(APP_BG);
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));

        panel.add(sectionHeader("Courses", ""), BorderLayout.NORTH);

        String[] cols = { "Code", "Title", "Credits", "Instructor" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = modernTable(model);
        refreshCourseTable(model, courses);

        panel.add(modernScroll(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(2, 0, 0, 0));

        JButton addBtn = new ThemeButton("Add Course", ACCENT, ACCENT_HOVER, ACCENT_PRESSED);
        JButton updateBtn = new ThemeButton("Update Course", SURFACE, SURFACE_2, SURFACE_2);
        JButton deleteBtn = new ThemeButton("Delete Course", DANGER, DANGER_HOVER, DANGER_PRESSED);

        updateBtn.setForeground(TEXT);

        actions.add(addBtn);
        actions.add(updateBtn);
        actions.add(deleteBtn);

        panel.add(actions, BorderLayout.SOUTH);

        // Create custom action listeners that handle the label safely
        ActionListener addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dummy label to pass to the listener
                JLabel dummyLabel = new JLabel();
                AddCourseListener listener = new AddCourseListener(frame, model, courses, courseStore, dummyLabel);
                // Override the behavior to not use the label
                listener.actionPerformed(e);
                refreshCourseTable(model, courses);
            }
        };

        UpdateCourseListener updateListener = new UpdateCourseListener(frame, table, model, courses, courseStore);

        ActionListener deleteListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel dummyLabel = new JLabel();
                DeleteCourseListener listener = new DeleteCourseListener(frame, table, model, courses, courseStore,
                        dummyLabel);
                listener.actionPerformed(e);
                refreshCourseTable(model, courses);
            }
        };

        addBtn.addActionListener(addListener);
        updateBtn.addActionListener(updateListener);
        deleteBtn.addActionListener(deleteListener);

        return panel;
    }

    private static JPanel sectionHeader(String title, String subtitle) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setForeground(TEXT);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        s.setForeground(MUTED);

        header.add(t);
        header.add(Box.createVerticalStrut(4));
        header.add(s);
        return header;
    }

    private static JTable modernTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? SURFACE : TABLE_ALT);
                }
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(new EmptyBorder(0, 10, 0, 10));
                }
                return c;
            }
        };

        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setGridColor(BORDER);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(SURFACE);
        table.setSelectionBackground(TABLE_SELECTION);
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setRowMargin(1);
        table.setBorder(new EmptyBorder(0, 0, 0, 0));

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setForeground(TEXT);
        header.setBackground(SURFACE_2);
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        centerRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));
        table.setDefaultRenderer(Object.class, centerRenderer);

        return table;
    }

    private static JScrollPane modernScroll(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(SURFACE);
        scroll.setBackground(SURFACE);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        return scroll;
    }

    private static void refreshStudentTable(DefaultTableModel model, RecordList<Student> students) {
        model.setRowCount(0);
        for (Student s : students.getItems()) {
            if (s.isFeePaid()) {
                model.addRow(new Object[] {
                        s.getStudentId(),
                        s.getName(),
                        s.getProgram(),
                        String.format("%.2f", s.calculateGPA()),
                        s.calculateGrade()
                });
            } else {
                model.addRow(new Object[] {
                        s.getStudentId(),
                        s.getName(),
                        s.getProgram(),
                        "Fee Pending",
                        "---"
                });
            }
        }
    }

    private static void refreshCourseTable(DefaultTableModel model, RecordList<Course> courses) {
        model.setRowCount(0);
        for (Course c : courses.getItems()) {
            model.addRow(new Object[] {
                    c.getCourseCode(),
                    c.getTitle(),
                    c.getCreditHours(),
                    c.getCrsInst().getName()
            });
        }
    }

    public static void preloadData(RecordList<Student> students, RecordList<Course> courses,
            DataStore<RecordList<Student>> studentStore,
            DataStore<RecordList<Course>> courseStore) {
        CourseInstructor csInstructor = new CourseInstructor("Dr. Ahmed", "PhD");
        CourseInstructor phyInstructor = new CourseInstructor("Prof. Khan", "MSc");
        CourseInstructor mathInstructor = new CourseInstructor("Dr. Sara", "PhD");

        Course cs = new Course("CS101", "Java Programming", 4, csInstructor);
        Course phy = new Course("PHY101", "Physics", 3, phyInstructor);
        Course math = new Course("MATH101", "Calculus", 4, mathInstructor);

        courses.addItem(cs);
        courses.addItem(phy);
        courses.addItem(math);

        Transcript t1 = new Transcript();
        t1.addResultEntry(new ResultEntry(cs, 85));
        t1.addResultEntry(new ResultEntry(phy, 75));
        t1.addResultEntry(new ResultEntry(math, 90));
        Student s1 = new ScienceStudent("Ali Raza", "Science", t1, true, "G1");

        Transcript t2 = new Transcript();
        t2.addResultEntry(new ResultEntry(cs, 78));
        t2.addResultEntry(new ResultEntry(phy, 82));
        t2.addResultEntry(new ResultEntry(math, 70));
        Student s2 = new ArtsStudent("Hassan Ahmed", "Arts", t2, true, "Painting");

        Transcript t3 = new Transcript();
        t3.addResultEntry(new ResultEntry(cs, 65));
        t3.addResultEntry(new ResultEntry(phy, 72));
        t3.addResultEntry(new ResultEntry(math, 68));
        Student s3 = new EngineeringStudent("Usman Khalid", "Engineering", t3, true, "TechCorp");

        Transcript t4 = new Transcript();
        t4.addResultEntry(new ResultEntry(cs, 88));
        t4.addResultEntry(new ResultEntry(phy, 91));
        t4.addResultEntry(new ResultEntry(math, 85));
        Student s4 = new ScienceStudent("Ahsan Farooq", "Science", t4, false, "G2");

        Transcript t5 = new Transcript();
        t5.addResultEntry(new ResultEntry(cs, 80));
        t5.addResultEntry(new ResultEntry(phy, 76));
        t5.addResultEntry(new ResultEntry(math, 79));
        Student s5 = new ArtsStudent("Bilal Tariq", "Arts", t5, true, "Music");

        students.addItem(s1);
        students.addItem(s2);
        students.addItem(s3);
        students.addItem(s4);
        students.addItem(s5);

        ArrayList<RecordList<Student>> tempS = new ArrayList<>();
        tempS.add(students);
        studentStore.saveToFile("students.dat", tempS);

        ArrayList<RecordList<Course>> tempC = new ArrayList<>();
        tempC.add(courses);
        courseStore.saveToFile("courses.dat", tempC);
    }

    static class ThemeButton extends JButton {
        private final Color normal;
        private final Color hover;
        private final Color pressed;

        ThemeButton(String text, Color normal, Color hover, Color pressed) {
            super(text);
            this.normal = normal;
            this.hover = hover;
            this.pressed = pressed;
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setForeground(normal.equals(SURFACE) ? TEXT : Color.WHITE);
            setBackground(normal);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 16, 10, 16));
            setContentAreaFilled(false);
            setOpaque(false);
            setRolloverEnabled(true);

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled())
                        setBackground(hover);
                }

                public void mouseExited(MouseEvent e) {
                    if (isEnabled())
                        setBackground(normal);
                }

                public void mousePressed(MouseEvent e) {
                    if (isEnabled())
                        setBackground(pressed);
                }

                public void mouseReleased(MouseEvent e) {
                    if (isEnabled())
                        setBackground(hover);
                }
            });
        }

        @Override
        public void setEnabled(boolean b) {
            super.setEnabled(b);
            setCursor(b ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color bg = isEnabled() ? getBackground() : DISABLED_BG;
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

            g2.setColor(isEnabled() ? new Color(255, 255, 255, 22) : DISABLED_TEXT);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
        }

        @Override
        public Insets getInsets() {
            return new Insets(10, 16, 10, 16);
        }
    }
}