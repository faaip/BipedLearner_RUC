package sample;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by frederikjuutilainen on 23/05/15.
 */
public class OutputDataWriter {
    String filename;
    List<CsvData> dataList = new ArrayList<>();

    public OutputDataWriter(String filename)
    {
        this.filename = Main.mode+"_"+filename+".csv";
    }


    public void add(CsvData data) {
         dataList.add(data);

    }

    public void createFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        fileWriter.append("column 1, column 2" + "\n");

        if(Main.mode == 0) {
            for (CsvData d : dataList) {
                fileWriter.append("" + d.generation);
                fileWriter.append(",");
                fileWriter.append("" + d.score);
                fileWriter.append("\n");
            }
        }else{
            for (CsvData d : dataList) {
                fileWriter.append("" + d.time);
                fileWriter.append(",");
                fileWriter.append("" + d.score);
                fileWriter.append("\n");
            }
        }

        fileWriter.flush();
        fileWriter.close();
    }


}
