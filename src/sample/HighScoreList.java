package sample;

import Rendering_dyn4j.ThreadSync;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreList {
    Object[][] data;
    Object[] columns = {"Generation #", "Total reward"};
    DefaultTableModel model = new DefaultTableModel(data,columns) {
        @Override
        public Class getColumnClass(int column) {
            switch (column) {
                case 0:
                    return Integer.class;
                case 1:
                    return Double.class;
                default:
                    return String.class;
            }
        }
    };
    public JTable table = new JTable(model){
    };

    public HighScoreList() {
        table.setAutoCreateRowSorter(true);
    }

    public void add(Generation g) {
        synchronized (ThreadSync.lock) {
            model.addRow(new Object[]{new Integer(g.generationNumber), new Double(g.accumulatedReward)});
        }
    }
}




