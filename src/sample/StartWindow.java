package sample;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SaraMac on 19/05/15.
 */
public class StartWindow {
    public static int modeSelected;
    public static boolean runNow = false;
    public StartWindow() {

        JFrame startFrame = new JFrame();
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setSize(350, 200);
        startFrame.setTitle("Start");


        JPanel startPanel = new JPanel();

        String[] modes = {"Mode 1", "Mode 2", "Mode 3"};
        JComboBox modeList = new JComboBox(modes);
        modeList.setMinimumSize(new Dimension(100, 100));

        modeList.addActionListener(e -> {
                    if (modeList.getSelectedIndex() == 0) modeSelected = 0;
                    if (modeList.getSelectedIndex() == 1) modeSelected = 1;
                    if (modeList.getSelectedIndex() == 2) modeSelected = 3;

                }
        );

        JButton start = new JButton("Start");
        start.setMinimumSize(new Dimension(100, 100));


        startPanel.setLayout(new GridLayout(0, 1));
        startPanel.add(new JLabel("Choose a mode: ", JLabel.CENTER));
        startPanel.add(modeList);
        startPanel.add(start);

        startFrame.add(startPanel);
        startFrame.setVisible(true);

        start.addActionListener(e -> {

            startFrame.setVisible(false);
            startFrame.dispose();
            Main.run(modeSelected);

        });
    }
}
