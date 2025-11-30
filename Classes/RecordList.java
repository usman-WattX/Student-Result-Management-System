// RecordList<T> (Generic Class)
//  A􀆩ributes: List<T> items
//  Methods: add(T item), remove(String id), getAll()
//  Usage: stores Students, Courses, or Transcripts

import java.util.*;
public class RecordList {
    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private ArrayList<Transcript> transcripts;

    public RecordList() {
        students = new ArrayList<Student>();
        courses = new ArrayList<Course>();
        transcripts = new ArrayList<Transcript>();
    }

    public RecordList(ArrayList<Student> students, ArrayList<Course> courses, ArrayList<Transcript> transcripts) {
        this.students = students;
        this.courses = courses;
        this.transcripts = transcripts;
    }


    public void addStudent(Student s){
        students.add(s);
    }
    public void removeStudent(String id){
        for(int i = 0; i < students.size(); i++){
            if (students.get(i).getStudentId().equals(id)) {
                students.remove(i);
            }
        }
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }


    public void addCourse(Course crs){
        courses.add(crs);
    }
    public void removeCourse(String id){
        for(int i = 0; i < courses.size(); i++){
            if (courses.get(i).getCourseCode().equals(id)) {
                courses.remove(i);
            }
        }
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }


    public void addTranscript(Transcript t){
        transcripts.add(t);
    }
    public void removeTranscript(String id){
        /* recordList removes items using remove(String id), so each type must have an id. Students use studentId and Courses use courseCode. Transcript has no ID or any identifier, so remove(String id) not gonna work. Tou phir Transcript ki class mai transcriptId ka data member bnana pare ga  ya phir transcript-student (composition) add krdo!
        */ 

        //if transcript-student composition
        // for(int i = 0; i < transcripts.size(); i++){
        //     if (transcripts.get(i).getStudent().getStudentId().equals(id)) {
        //         transcripts.remove(i);
        //     }
        // }

        //if trnscript has id data member
        // for(int i = 0; i < transcripts.size(); i++){
        //     if (transcripts.get(i).getTranscriptId().equals(id)) {
        //         transcripts.remove(i);
        //     }
        // }
    }
    public ArrayList<Transcript> getTranscripts() {
        return transcripts;
    }
    public void setTranscripts(ArrayList<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        System.out.println("\nALL STUDENTS");
        for (int i = 0; i < students.size(); i++) {
            System.out.println("\n" + students.get(i).toString());
        }
    }

    public void showAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No Courses found!");
            return;
        }

        System.out.println("\nALL COURSES");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("\n" + courses.get(i).toString());
        }
    }

    public void showAllTranscripts() {
        if (students.isEmpty()) {
            System.out.println("No Transcripts found!");
            return;
        }

        System.out.println("\nALL STUDENTS");
        for (int i = 0; i < transcripts.size(); i++) {
            System.out.println("\n" + transcripts.get(i).toString());
        }
    }

}
