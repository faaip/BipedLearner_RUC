package sample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreList{

    DefaultListModel<String> model = new DefaultListModel<>();
    ArrayList<Generation> aList = new ArrayList<>();
    public JList<String> jList = new JList<>(model);


    public HighScoreList() {

    }

    public void add(Generation g) {
        aList.add(g);

        jList.clearSelection();
        model.clear();
        for (Generation s : aList) {
            model.addElement("#" + s.generationNumber + " " + s.accumulatedReward);
        }
    }

}
