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
    List<CsvData> dataList = new ArrayList<>();

    public OutputDataWriter()
    {
        this.filename = "Test_" + Main.mode;
    }


    public void add(CsvData data) {
         dataList.add(data);

    }

    public void createFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        fileWriter.append("column 1, column 2" + "\n");

        for (CsvData d : dataList)
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
