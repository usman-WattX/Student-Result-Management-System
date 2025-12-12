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
            }
        }

        if (!found) {
            System.out.println("ID Not Found!");
        }
    }
}
