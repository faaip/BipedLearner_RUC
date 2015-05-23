package sample;


public class Data {
    int generation;
    double score;
    double time;

    public Data(int generation, double score) {
        this.generation = generation;
        this.score = score;
    }

    public Data(double time, double score) {
        this.time = time;
        this.score = score;
    }

}
