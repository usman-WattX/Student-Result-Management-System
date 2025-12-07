// RecordList<T> (Generic Class)
//  A􀆩ributes: List<T> items
//  Methods: add(T item), remove(String id), getAll()
//  Usage: stores Students, Courses, or Transcripts
import java.util.*;
import java.io.*;

public class RecordList<T> implements Serializable{ 
    private ArrayList<T> items;

    public RecordList() {
        this(new ArrayList<T>());
    }
    public RecordList(ArrayList<T> items) {
        setItems(items);
    }

    public ArrayList<T> getItems() {
        return items;
    }
    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    public void addItem(T item){
        items.add(item);
    }

    public void removeItem(String id) {
        boolean found = false;

        for (int i = 0; i < items.size(); i++) {
            T obj = items.get(i);

            if (obj instanceof Student && ((Student) obj).getStudentId().equals(id)) {
                items.remove(i);
                found = true;
                System.out.println("Student Removed Successfully!");
                break;
            } else if (obj instanceof Course && ((Course) obj).getCourseCode().equals(id)) {
                items.remove(i);
                found = true;
                System.out.println("Course Removed Successfully!");
                break;
            } else if (obj instanceof Transcript && ((Transcript) obj).getStudentId().equals(id)) {
                items.remove(i);
                break;
            }
        }

        if (!found) {
            System.out.println("ID Not Found!");
        }
    }



    public void showAllItems() {
        if (items.isEmpty()) {
            System.out.println("No Records Found!");
            return;
        }

        if (items.get(0) instanceof Student) {
            System.out.println("ALL STUDENTS:");
        } else if (items.get(0) instanceof Course) {
            System.out.println("ALL COURSES:");
        } else if (items.get(0) instanceof Transcript) {
            System.out.println("ALL TRANSCRIPTS:");
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }
    }
}
