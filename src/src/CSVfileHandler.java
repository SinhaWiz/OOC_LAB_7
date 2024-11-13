import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
public class CSVfileHandler implements FileOperations {
    @Override
    public void rename(String oldName, String newName) {
        File oldFile  = new File(oldName);
        File newFile = new File(newName);
        oldFile.renameTo(newFile);
        if(!oldFile.renameTo(newFile)){
            throw new RuntimeException("Rename failed");
        }
    }

    @Override
    public List<String[]> readFile(String filename) {
        List<String[]> data = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String  line ;
            while((line = br.readLine()) != null){
                data.add(line.split(","));
            }
            br.close();
        }
        catch(IOException e){
            throw new RuntimeException("Error reading file");
        }
        return data;
    }

    @Override
    public void writeFile(String fileName, List<String[]> data) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for(String[] row : data){
                bw.write(String.join(",",row));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException e){
            throw new RuntimeException("Write failed");
        }

    }
}
