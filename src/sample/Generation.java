package sample;

public class Generation implements Comparable {

    double accumulatedReward;
    int generationNumber;
    String name;

    public Generation(int generationNumber, double accumulatedReward, int noOfStatesExplored) {
        this.generationNumber = generationNumber;
        this.accumulatedReward = accumulatedReward;
    }


    @Override
    public int compareTo(Object o) {
        Generation g = (Generation) o;

        if (g.accumulatedReward > this.accumulatedReward) {
            return 1;
        }
        if (g.accumulatedReward < this.accumulatedReward) {
            return -1;
        }

        return 0;
    }
}
