package sample;

import Rendering_dyn4j.ThreadSync;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreList {

    DefaultListModel<String> model = new DefaultListModel<>();
    ArrayList<Generation> aList = new ArrayList<>();
    public JList<String> jList = new JList<>(model);


    public HighScoreList() {
    }

    public void add(Generation g)  {
        model.clear();
        aList.add(g);
        Collections.sort(aList);
        for (Generation s : aList) {
            synchronized (ThreadSync.lock) {
                model.addElement("#" + s.generationNumber + " - " + new DecimalFormat("#.###").format(s.accumulatedReward));
            }
        }
    }
}
