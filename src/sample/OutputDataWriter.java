package sample;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frederikjuutilainen on 23/05/15.
 */
public class OutputDataWriter {
    String filename;
    List<Data> dataList = new ArrayList<>();

    public OutputDataWriter(String filename)
    {
        this.filename = filename + ".csv";
    }


    public void add(Data data) {
         dataList.add(data);

    }

    public void createFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        for (Data d : dataList)
        {
            fileWriter.append(""+d.generation);
            fileWriter.append(",");
            fileWriter.append(""+ d.score);
            fileWriter.append("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }


}
