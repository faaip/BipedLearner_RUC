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
    String[] columnNames = {"Generation #", "Accumulated Reward"};
    DefaultTableModel model = new DefaultTableModel();
    public JTable table = new JTable(model);
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

    public HighScoreList() {
        model.addColumn("Generation #");
        model.addColumn("Accumulated reward");
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        table.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer() {
            private NumberFormat numberFormat = new DecimalFormat("#,###,###");
            @Override
            protected void setValue(Object aValue) {
                Integer value = (Integer) aValue;
                super.setValue(numberFormat.format(value));
            }
        });
    }

    public void add(Generation g) {
        synchronized (ThreadSync.lock) {
//            model.addRow(new Object[][]{g.generationNumber, new Double(g.accumulatedReward)});
            model.addRow(new Double[]{(double)g.generationNumber,g.accumulatedReward});
//            model.addRow(new Object[]{g.generationNumber, new DecimalFormat("#.###").format(g.accumulatedReward)});
        }




    }


}




