package sample;

import javax.swing.*;

/*
This state creates and input dialog, where the user can choose a reward mode for the bi-ped learner.
The mode is set dependent on the mode chosen in the dropdown menu.
 */

public class ModeSelection {
    public void askUser() {
        String[] rewardModes = {"Reward for forward motion", "Reward for elevated feet", "Reward for bending one knee", "Reward for upright position", "Reward for backwards motion"};
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Choose behaviour:",
                "Bi-Ped Learner",
                JOptionPane.PLAIN_MESSAGE,
                null,
                rewardModes,
                rewardModes[0]);
        if (s.equals("Reward for forward motion")) {
            Main.mode = 0;
        }
        if (s.equals("Reward for elevated feet")) {
            Main.mode = 1;
        }
        if (s.equals("Reward for bending one knee")) {
            Main.mode = 2;
        }
        if(s.equals("Reward for upright position")){
            Main.mode = 3;
        }
        if(s.equals("Reward for backwards motion")){
            Main.mode = 4;
        }

    }
}

