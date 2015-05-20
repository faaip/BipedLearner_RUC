package sample;


import javax.swing.*;
import java.awt.*;

public class ModeSelection {

  public void askUser() {
      String[] rewardModes = {"Reward for forward motion", "Reward for elevated feet", "Reward for bending one knee"};
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Choose behaviour",
                "Bi-Ped Learner",
                JOptionPane.PLAIN_MESSAGE,
                null,
                rewardModes,
                rewardModes[0]);
        if(s.equals("Reward for forward motion")){Main.mode = 0;}
      if(s.equals("Reward for elevated feet")){Main.mode = 1;}
      if(s.equals("Reward for bending one knee")){Main.mode = 2;}

      Main.run();
    }}

