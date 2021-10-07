package gui;

import model.HockeyTeam;
import model.QualifiedTeams;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


// Hockey Team Application GUI.
// ** Borrows features from ListDemo.java from oracle
public class HockeyGUI extends JPanel implements ListSelectionListener {
    public static QualifiedTeams qualifiedTeams;
    private static final String addTeamString = "Add Team";
    private static final String addPlayerString = "Add Player";
    private static final String viewPlayerString = "View Players";
    private static final String addGoalString = "Add Goal";
    private static final String addAssistString = "Add Assist";
    private static final String retirePlayerString = "Retire Player";
    private static final String retireTeamString = "Retire Team";
    public static JFrame frame = new JFrame("Hockey Team Application");
    public static final String JSON_STORE = "./data/hockeyTeams.json";
    private static final ImageIcon icon = new ImageIcon("./pictures/nhl.png");
    private JButton addTeamButton;
    private JButton addPlayerButton;
    private JButton viewPlayersButton;
    private JButton addGoalButton;
    private JButton addAssistButton;
    private JButton retirePlayerButton;
    private JButton retireTeamButton;
    public static JList<String> teams;
    public static JList<String> players;
    public static JTextField teamName;
    public static JTextField playerName;
    public static DefaultListModel<String> teamModel;
    public static DefaultListModel<String> playersModel;
    public static boolean load;
    public static JsonWriter jsonWriter;
    public static JsonReader jsonReader;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    // Loads + runs hockey application
    public HockeyGUI() {
        super(new BorderLayout());

        // load teams depending on user input:
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        if (load) {
            try {
                qualifiedTeams = jsonReader.read();
            } catch (IOException e) {
                playSound("./sounds/error.wav", 0.10); // play appropriate sound effect
            }
        } else {
            qualifiedTeams = new QualifiedTeams();
        }

        // helper functions for GUI:
        JScrollPane listScrollPane = setUpTeams(qualifiedTeams);
        JScrollPane listScrollPane2 = setUpPlayers();
        setUpButtons(qualifiedTeams);
        drawGUI(listScrollPane, listScrollPane2);
    }

    // MODIFIES: this
    // EFFECTS: sets up player scroll pane - begins empty
    private JScrollPane setUpPlayers() {
        playersModel = new DefaultListModel<>();
        players = new JList<>(playersModel);
        players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        players.setSelectedIndex(0);
        players.addListSelectionListener(this);
        return new JScrollPane(players);
    }

    // MODIFIES: this
    // EFFECTS: sets up team scroll pane - begins with loaded teams
    private JScrollPane setUpTeams(QualifiedTeams qualified) {
        teamModel = new DefaultListModel<>();

        // add all team names to pane
        for (HockeyTeam t : qualified.getTeams()) {
            teamModel.addElement(t.getTeamName());
        }

        teams = new JList<>(teamModel);
        teams.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teams.setSelectedIndex(0);
        teams.addListSelectionListener(this);
        teams.setVisibleRowCount(5);
        return new JScrollPane(teams);
    }

    // MODIFIES: this
    // EFFECTS: sets up + configures all buttons
    private void setUpButtons(QualifiedTeams qualifiedTeams) {
        setUpRetirePlayerButton(qualifiedTeams);
        setUpRetireTeamButton(qualifiedTeams);
        setUpAddGoalButton(qualifiedTeams);
        setUpAddAssistButton(qualifiedTeams);
        setUpViewPlayersButton(qualifiedTeams);
        setUpAddTeamButton(qualifiedTeams);
        setUpAddPlayerButton(qualifiedTeams);
    }

    // EFFECTS: sets up + configures add player button
    private void setUpAddPlayerButton(QualifiedTeams qualifiedTeams) {
        addPlayerButton = new JButton(addPlayerString);
        AddPlayerListener addplayerlistener = new AddPlayerListener(addPlayerButton, qualifiedTeams);
        addPlayerButton.setActionCommand(addPlayerString);
        addPlayerButton.addActionListener(addplayerlistener);
        addPlayerButton.setEnabled(false);

        playerName = new JTextField(10);
        playerName.addActionListener(addplayerlistener);
        playerName.getDocument().addDocumentListener(addplayerlistener);
    }

    // EFFECTS: sets up + configures retire player button
    private void setUpRetirePlayerButton(QualifiedTeams qualifiedTeams) {
        retirePlayerButton = new JButton(retirePlayerString);
        RetirePlayerListener retireplayerlistener = new RetirePlayerListener(qualifiedTeams);
        retirePlayerButton.setActionCommand(retirePlayerString);
        retirePlayerButton.addActionListener(retireplayerlistener);
    }

    // EFFECTS: sets up + configures retire team button
    private void setUpRetireTeamButton(QualifiedTeams qualifiedTeams) {
        retireTeamButton = new JButton(retireTeamString);
        RetireTeamListener retireteamlistener = new RetireTeamListener(qualifiedTeams);
        retireTeamButton.setActionCommand(retireTeamString);
        retireTeamButton.addActionListener(retireteamlistener);
    }

    // EFFECTS: sets up + configures add team button
    private void setUpAddTeamButton(QualifiedTeams qualifiedTeams) {
        addTeamButton = new JButton(addTeamString);
        AddTeamListener addteamlistener = new AddTeamListener(addTeamButton, qualifiedTeams);
        addTeamButton.setActionCommand(addTeamString);
        addTeamButton.addActionListener(addteamlistener);
        addTeamButton.setEnabled(false);

        teamName = new JTextField(10);
        teamName.addActionListener(addteamlistener);
        teamName.getDocument().addDocumentListener(addteamlistener);
    }

    // EFFECTS: sets up + configures view players button
    private void setUpViewPlayersButton(QualifiedTeams qualifiedTeams) {
        viewPlayersButton = new JButton(viewPlayerString);
        ViewPlayersListener viewplayerslistener = new ViewPlayersListener(qualifiedTeams);
        viewPlayersButton.setActionCommand(viewPlayerString);
        viewPlayersButton.addActionListener(viewplayerslistener);
    }

    // EFFECTS: sets up + configures add assist button
    private void setUpAddAssistButton(QualifiedTeams qualifiedTeams) {
        addAssistButton = new JButton(addAssistString);
        AddAssistListener addassistlistener = new AddAssistListener(qualifiedTeams);
        addAssistButton.setActionCommand(addAssistString);
        addAssistButton.addActionListener(addassistlistener);
    }

    // EFFECTS: sets up + configures add goal button
    private void setUpAddGoalButton(QualifiedTeams qualifiedTeams) {
        addGoalButton = new JButton(addGoalString);
        AddGoalListener addgoallistener = new AddGoalListener(qualifiedTeams);
        addGoalButton.setActionCommand(addGoalString);
        addGoalButton.addActionListener(addgoallistener);
    }

    // MODIFIES: this
    // EFFECTS: draws buttons + panels to screen + displays to user.
    private void drawGUI(JScrollPane listScrollPane, JScrollPane listScrollPane2) {
        JPanel buttonPane1 = new JPanel();
        JLabel label = new JLabel(icon);
        buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.LINE_AXIS));
        buttonPane1.add(viewPlayersButton);
        buttonPane1.add(Box.createHorizontalStrut(5));
        buttonPane1.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane1.add(Box.createHorizontalStrut(5));
        buttonPane1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPane1.add(label);

        JPanel buttonPane2 = new JPanel();
        buttonPane2.setLayout(new GridLayout(0,2));
        buttonPane2.add(addTeamButton);
        buttonPane2.add(teamName);
        buttonPane2.add(addPlayerButton);
        buttonPane2.add(playerName);
        buttonPane2.add(addGoalButton);
        buttonPane2.add(addAssistButton);
        buttonPane2.add(retireTeamButton);
        buttonPane2.add(retirePlayerButton);

        add(listScrollPane, BorderLayout.WEST);
        add(listScrollPane2, BorderLayout.EAST);
        add(buttonPane1, BorderLayout.BEFORE_FIRST_LINE);
        add(buttonPane2, BorderLayout.AFTER_LAST_LINE);
    }

    public static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // opens load GUI when GUI is first run:
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                HockeyGUI.confirmAndOpen();
            }
        });

        // opens save GUI when GUI is exited:
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HockeyGUI.confirmAndExit();
            }
        });

        // run GUI:
        JComponent newContentPane = new HockeyGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: saves data to file depending on user input + closes GUI
    public static void confirmAndExit() {
        playSound("./sounds/boo.wav", 0.07);
        if (JOptionPane.showConfirmDialog(
                frame,
                "Save data before quitting?",
                "Please confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
        ) {
            try {
                jsonWriter.open();
                jsonWriter.write(qualifiedTeams);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: opens data from file depending on user input + opens GUI
    public static void confirmAndOpen() {
        load = JOptionPane.showConfirmDialog(
                frame,
                "Load data before opening?",
                "Please confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        // reset GUI with new teams:
        JComponent newContentPane = new HockeyGUI();
        frame.setContentPane(newContentPane);
        frame.pack();
        playSound("./sounds/horn.wav", 0.15); // play appropriate sound effect
    }

    // EFFECTS: plays sound to user if file exists
    public static void playSound(String soundName, double vol) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip.open(audioInputStream);
            setVol(vol, clip);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error! File not found!");
        }
    }

    // EFFECTS: controls volume of played sounds
    public static void setVol(double vol, Clip clip) {
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float decibels = (float) (Math.log(vol) / Math.log(10) * 20);
        gain.setValue(decibels);
    }

    @Override
    //Required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            //No selection, disable button.
            //Selection, enable button.
            viewPlayersButton.setEnabled(teams.getSelectedIndex() != -1);
        }
    }
}


