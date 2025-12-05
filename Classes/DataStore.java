import java.io.*;
import java.util.*;

public class DataStore {
    public void saveToFile(String fileName, Object item) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName, true);
            oos = new AppendableObjectOutputStream(fos);
            oos.writeObject(item);
            System.out.println("Object saved/appended to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving object: " + e.getMessage());
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                System.err.println("Error closing streams: " + e.getMessage());
            }
        }
    }
    public void saveToFile(String fileName, List<Object> items) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            for (Object item : items) {
                oos.writeObject(item);
            }
            System.out.println("List of objects saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving list: " + e.getMessage());
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                System.err.println("Error closing streams: " + e.getMessage());
            }
        }
    }
    public List<Object> loadFromFile(String fileName) {
        List<Object> items = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            while (true) {
                try {
                    Object obj = ois.readObject();
                    items.add(obj);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                }
            }
            System.out.println("Data loaded from " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        } finally {
            try {
                if (ois != null)
                    ois.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                System.err.println("Error closing streams: " + e.getMessage());
            }
        }
        return items;
    }
}

class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
    }
}
