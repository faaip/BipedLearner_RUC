package sample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreList {

    DefaultListModel<String> model = new DefaultListModel<>();
    ArrayList<Generation> aList = new ArrayList<Generation>();
    public JList<String> jList = new JList<>(model);


    public HighScoreList() {

    }

    public void add(Generation g) {
        aList.add(new Generation(Math.random()));

        Collections.sort(aList);

        model.clear();

        for(Generation s :aList){
            model.addElement(s.accumulatedReward+"");
        }
    }


}
