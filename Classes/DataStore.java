import java.util.*;
import java.io.*;
@SuppressWarnings("unchecked")

public class DataStore <T>{
    
    public void saveToFile(String fileName, ArrayList<T> items){

        if(items == null || items.isEmpty()){
            System.out.println("Nothing to Save!");
            return;
        }

        FileOutputStream fileOut = null;
        ObjectOutputStream writer = null;
        try {
            fileOut = new FileOutputStream(fileName, true);
            writer = new Append(fileOut);
            
            for (int i = 0; i < items.size(); i++){
                writer.writeObject(items.get(i));
            }

            System.out.println("Data Saved Successfully!\n"); 
        } catch(FileNotFoundException e){
            System.out.println("File Not Find:: " + fileName);
        } catch(IOException e){
            System.out.println("Error Writing the File" + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
                if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error Closing Writer: " + e.getMessage());
                }
            }
        }
    }

    public void saveToFile(String fileName, T item){

        if(item == null){
            System.out.println("Nothing to Save!");
            return;
        }

        FileOutputStream fileOut = null;
        ObjectOutputStream writer = null;
        try {
            fileOut = new FileOutputStream(fileName, true);
            writer = new Append(fileOut);
            
            writer.writeObject(item);
            
            System.out.println("Object Saved Successfully!\n"); 
        } catch(FileNotFoundException e){
            System.out.println("File Not Find:: " + fileName);
        } catch(IOException e){
            System.out.println("Error Writing the File" + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
                if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error Closing Writer: " + e.getMessage());
                }
            }
        }
    }

    public ArrayList<T> loadFromFile(String fileName){

        ArrayList<T> items = new ArrayList<>();

        File fl = new File(fileName);
        if(!fl.exists()){
            return items;
        }

        FileInputStream fileIn = null; 
        ObjectInputStream reader= null; 
        try{
            fileIn = new FileInputStream(fileName);
            reader = new ObjectInputStream(fileIn);
            
            while(true){
                try {
                    T item = (T) reader.readObject();
                    items.add(item);
                } catch (EOFException e) {
                    break;
                }
            }

            System.out.println("Data Loaded from File: " + fileName);
        }catch(FileNotFoundException e){
            System.out.println("File Not Find: " + fileName);
        }catch(IOException e){
            System.out.println("Error Reading the File: " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("Class Type Mismatch: " + e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error Closing Reader: " + e.getMessage());
                }
            }
        }
        return items;
    }
}

class Append extends ObjectOutputStream{

    public Append(OutputStream file) throws IOException{
        super(file);
    }

    public void writeStreamHeader() throws IOException{
        reset();
    }
}