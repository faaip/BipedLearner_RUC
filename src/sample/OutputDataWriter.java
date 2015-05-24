package sample;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OutputDataWriter {
    String type = "150_01_09_1";     // Rplus, gamma, alpha. ne
    String filename;
    List<CsvData> dataList = new ArrayList<>();

    public OutputDataWriter(String filename) {
//        this.filename = "Mode_" + Main.mode + "_" + type + ".csv";
        this.filename = "Mode_" + Main.mode + "_" + Main.agent.getRplus() + "_"+ Main.agent.getAlpha() + "_"+ Main.agent.getGamma() + "_"+ Main.agent.getNe()+".csv";

    }


    public void add(CsvData data) {
        dataList.add(data);

    }

    public void createFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        fileWriter.append("column 1, column 2" + "\n");

        if (Main.mode == 0) {
            for (CsvData d : dataList) {
                fileWriter.append("" + d.generation);
                fileWriter.append(",");
                fileWriter.append("" + d.score);
                fileWriter.append("\n");
            }
        } else {
            for (CsvData d : dataList) {
                fileWriter.append("" + d.time);
                fileWriter.append(",");
                fileWriter.append("" + d.score);
                fileWriter.append("\n");
            }
        }

        // Output
        fileWriter.flush();
        fileWriter.close();
    }


}
