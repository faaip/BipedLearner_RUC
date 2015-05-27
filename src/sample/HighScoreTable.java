package sample;

import Rendering_dyn4j.ThreadSync;
import javax.swing.*;
import javax.swing.table.*;

/*
This class contains a list of each generation of walkers. It is only added to the gui if the mode is set to reward for forward motion.
The class contains a JTable and a method for adding a new generation of walkers to the table after each iteration.
 */

public class HighScoreTable {
    private Object[][] data;
    private Object[] columns = {"Generation #", "Total reward"}; // TODO FJERN NUMBER OF NEW STATES
    private DefaultTableModel model = new DefaultTableModel(data,columns) {
        @Override // This method is overriden to ensure correction sorting in the table
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
    private JTable table = new JTable(model);
    public HighScoreTable() {
        table.setAutoCreateRowSorter(true); // Table is automatically sorted
    }

    public void add(Generation g) {
        synchronized (ThreadSync.lock) { // The method for adding rows is synchronized to the threadsync object
            model.addRow(new Object[]{new Integer(g.generationNumber), new Double(g.accumulatedReward)});
        }
    }
    public JTable getTable() {
        return table;
    }


}




