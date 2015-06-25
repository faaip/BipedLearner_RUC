package sample;

/*
This class is for creating generation objects that saves information. These are shown in the table in the GUI.
 */

public class Generation  {
    double accumulatedReward;
    int generationNumber;
    public Generation(int generationNumber, double accumulatedReward) {
        // This constructor takes information from each ended iteration of bi-ped walkers
        this.generationNumber = generationNumber;
        this.accumulatedReward = accumulatedReward;
    }
}