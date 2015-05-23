package sample;


public class CsvData {
    int generation;
    double score;
    double time;

    public CsvData(int generation, double score) {
        this.generation = generation;
        this.score = score;
    }

    public CsvData(double time, double score) {
        this.time = time;
        this.score = score;
    }

}
