package sample;


public class CsvData {
    long actionNumber;
    double score;
    double time;

    public CsvData(long actionNumber, double score) {
        this.actionNumber = actionNumber;
        this.score = score;
    }

    public CsvData(double time, double score) {
        this.time = time;
        this.score = score;
    }

}
