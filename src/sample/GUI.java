package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;
import org.dyn4j.geometry.Vector2;

import java.awt.*;


//TODO Comment and clean
public class GUI {


    public static int simulationSpeed = 0;
    static JLabel generationNo = new JLabel("Generation # " + 0);
    static JLabel highScore = new JLabel("Highscore: ");
    public static HighScoreList highScoreList = new HighScoreList();
    public static int modeSelected = 0;


    public GUI(Graphics2D world) {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(350, 1000);
        gui.setTitle("Controls");


        JPanel startPanel = new JPanel();
        //startPanel.setMinimumSize(new Dimension(100,100));

        String[] modes = {"Reward for walking forward", "Reward for elevated feet", "Reward for bending one knee"};
        JComboBox modeMenu = new JComboBox(modes);

        JLabel modeWalking = new JLabel ("Reward for walking forward",JLabel.CENTER);
        JLabel modeFeet = new JLabel ("Reward for elevated feet",JLabel.CENTER);
        JLabel modeKnee = new JLabel ( "Reward for bending one knee",JLabel.CENTER);
        JLabel chooseLabel = new JLabel("You need to choose a reward mode: ", JLabel.CENTER);

        modeMenu.addActionListener(e -> {
                    Main.mode = modeMenu.getSelectedIndex();
                    System.out.println(Main.mode);

//                    if (modeMenu.getSelectedIndex() == 0) {modeSelected = 0; startPanel.add(modeWalking);}
//                    if (modeMenu.getSelectedIndex() == 1) {modeSelected = 1; startPanel.add(modeFeet);}
//                    if (modeMenu.getSelectedIndex() == 2) {modeSelected = 2; startPanel.add(modeKnee);}
                }
        );

        JButton start = new JButton("Start");

        startPanel.setLayout(new GridLayout(0, 1));
        startPanel.add(chooseLabel);

        startPanel.add(modeMenu);
        startPanel.add(start);

        start.addActionListener(e -> {
            simulationSpeed = 1;
            start.setVisible(false);
            modeMenu.setVisible(false);
            chooseLabel.setVisible(false);
        });



        // Add controls
//        JButton skip1 = new JButton("Skip 1 minute");
//        JButton skip10 = new JButton("Skip 10 minutes");
        JButton reset = new JButton("Reset walker");
        JSlider amountSlider = new JSlider(1, 25, 1);
        JLabel simSpeed = new JLabel(simulationSpeed + " x Speed");


        // Panel for controls
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        controls.setBackground(Color.white);


        amountSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {
                // TODO pause simulation while slider is adjusted
                simulationSpeed = amountSlider.getValue();
                world.step((int) Math.floor(amountSlider.getValue() / world.getStepFrequency()));
            }
            simSpeed.setText(simulationSpeed + " x Speed");
        });

        reset.addActionListener(e -> {
//            Graphics2D.walker.resetPosition();
//            Graphics2D.walker.torso.applyImpulse(-75);
            Main.simulationRunning = false;
            System.out.println(Main.simulationRunning);

        });


        controls.add(reset);
        controls.add(amountSlider);
        controls.add(simSpeed);



        // Panel for info monitoring
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 3));
        info.setBackground(Color.yellow);


        //todo add labels


        JPanel highScores = new JPanel();
        highScores.add(new JScrollPane(highScoreList.jList));

        info.add(highScores);
        info.add(generationNo);
        info.add(highScore);
//         info.add(exploringLabel);
//         info.add(actionLabel);
//         info.add(NsaLabel);
        // info.add(currentStateLabel);
        // info.add(currentGenLabel);

        // Panels are added to splitpane




        JSplitPane split = new JSplitPane();
        split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        split.setRightComponent(controls);
        split.setLeftComponent(info);


       // modeFeet.setVisible(false);
        //modeKnee.setVisible(false);
        //modeWalking.setVisible(false);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,startPanel,split);
        gui.add(mainSplit);
        gui.setVisible(true);




        // Panel for graphs
        //   JPanel graphs = new JPanel();
        //  graphs.setLayout(new GridBagLayout());
        //   graphs.setBackground(Color.BLACK);

    }

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void update() {

        generationNo.setText("Generation #" + Main.generation);
        highScore.setText("Highscore was gen. #" + Main.bestGeneration + " reward: ");
    }


}
