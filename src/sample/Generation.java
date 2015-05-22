package sample;

/*
This class is for creating generation objects that saves information
 */

public class Generation  {

    double accumulatedReward;
    int generationNumber;
    int noOfStatesExplored;

    public Generation(int generationNumber, double accumulatedReward, int noOfStatesExplored) {
        // This constructor takes information from each ended iteration of bi-ped walkers´
        this.generationNumber = generationNumber;
        this.accumulatedReward = accumulatedReward;
        this.noOfStatesExplored = noOfStatesExplored;
    }
}
