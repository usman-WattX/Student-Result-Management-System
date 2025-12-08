import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DataStore<T> {

    public void appendToFile(String fileName, T item) {
        if (item == null)
            return;
        try (FileOutputStream fos = new FileOutputStream(fileName, true);
                ObjectOutputStream oos = new Append(fos)) {
            oos.writeObject(item);
            System.out.println("Object appended successfully!");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    public ArrayList<T> loadFromFile(String fileName) {
        ArrayList<T> items = new ArrayList<>();
        File f = new File(fileName);
        if (!f.exists())
            return items;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                try {
                    T item = (T) ois.readObject();
                    items.add(item);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Data loaded from file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return items;
    }

    public void updateFile(String fileName, ArrayList<T> items) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (T item : items) {
                oos.writeObject(item);
            }
            System.out.println("File updated successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}

class Append extends ObjectOutputStream {
    public Append(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }
}
