package sample;

public class Generation implements Comparable{

     double accumulatedReward;
    int generationNumber;

    public Generation(double accumulatedReward)
    {
        this.accumulatedReward = accumulatedReward;
    }

    @Override
    public int compareTo(Object o) {
        Generation g = (Generation) o;

        if(g.accumulatedReward > this.accumulatedReward)
        {return -1;}
        if(g.accumulatedReward < this.accumulatedReward)
        {return -1;}

        return 0;
    }
}
