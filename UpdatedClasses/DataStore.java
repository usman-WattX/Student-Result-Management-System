import java.io.*;
import java.util.ArrayList;
@SuppressWarnings("unchecked")

public class DataStore<T extends Serializable> {

    public void saveToFile(String fileName, ArrayList<T> items) {

        if (items == null || items.isEmpty()) {
            System.out.println("Nothing to Save!");
            return;
        }

        ObjectOutputStream writer = null;
        FileOutputStream fileOut = null;

        try {
            fileOut = new FileOutputStream(fileName);
            writer = new ObjectOutputStream(fileOut);

            writer.writeObject(items);
            System.out.println("Data Saved Successfully!\n");

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error Writing the File: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error Closing Writer: " + e.getMessage());
                }
            }
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    System.out.println("Error Closing FileOutputStream: " + e.getMessage());
                }
            }
        }
    }

    public ArrayList<T> loadFromFile(String fileName) {

        ArrayList<T> items = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream reader = null;
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist: " + fileName);
            return items;
        }

        try {
            fileIn = new FileInputStream(fileName);
            reader = new ObjectInputStream(fileIn);

            items = (ArrayList<T>) reader.readObject();
            System.out.println("Data Loaded Successfully from: " + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error Reading the File: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Type Mismatch: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error Closing Reader: " + e.getMessage());
                }
            }
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    System.out.println("Error Closing FileInputStream: " + e.getMessage());
                }
            }
        }
        return items;
    }
}
