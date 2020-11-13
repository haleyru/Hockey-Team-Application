package gui;

import model.HockeyPlayer;
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

// Hockey Team Application GUI
// ** Borrows features from ListDemo.java from oracle
public class HockeyGUI extends JPanel implements ListSelectionListener {
    public static QualifiedTeams qualifiedTeams;
    private JList<String> teams;
    private JList<String> players;
    private DefaultListModel<String> teamModel;
    private DefaultListModel<String> playersModel;
    private static final String addTeamString = "Add Team";
    private static final String addPlayerString = "Add Player";
    private static final String viewPlayerString = "View Players";
    private static final String addGoalString = "Add Goal";
    private static final String addAssistString = "Add Assist";
    private static final String retirePlayerString = "Retire Player";
    private static final String retireTeamString = "Retire Team";
    private JButton addTeamButton;
    private JButton addPlayerButton;
    private JButton viewPlayersButton;
    private JButton addGoalButton;
    private JButton addAssistButton;
    private JButton retirePlayerButton;
    private JButton retireTeamButton;
    private JTextField teamName;
    private JTextField playerName;
    private static boolean load;
    public static JFrame frame = new JFrame("Hockey Team Application");
    private static final String JSON_STORE = "./data/hockeyTeams.json";
    private static JsonWriter jsonWriter;

    // EFFECTS: loads + runs hockey application
    public HockeyGUI() {
        super(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        JsonReader jsonReader = new JsonReader(JSON_STORE);

        // load teams depending on user input
        if (load) {
            try {
                qualifiedTeams = jsonReader.read();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        } else {
            qualifiedTeams = new QualifiedTeams();
        }

        // helper functions for GUI
        JScrollPane listScrollPane = setUpTeams(qualifiedTeams);
        JScrollPane listScrollPane2 = setUpPlayers();
        setUpButtons();
        drawGUI(listScrollPane, listScrollPane2);
    }

    // MODIFIES: this
    // EFFECTS: sets up player scroll pane
    private JScrollPane setUpPlayers() {
        playersModel = new DefaultListModel<>();
        players = new JList<>(playersModel);
        players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        players.setSelectedIndex(0);
        players.addListSelectionListener(this);
        return new JScrollPane(players);
    }

    // MODIFIES: this
    // EFFECTS: sets up team scroll pane
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
    private void setUpButtons() {
        setUpRetirePlayerButton();
        setUpRetireTeamButton();
        setUpAddGoalButton();
        setUpAddAssistButton();
        setUpViewPlayersButton();
        setUpAddTeamButton();
        setUpAddPlayerButton();
    }

    // EFFECTS: sets up + configures add player button
    private void setUpAddPlayerButton() {
        addPlayerButton = new JButton(addPlayerString);
        AddPlayerListener addplayerlistener = new AddPlayerListener(addPlayerButton);
        addPlayerButton.setActionCommand(addPlayerString);
        addPlayerButton.addActionListener(addplayerlistener);
        addPlayerButton.setEnabled(false);

        playerName = new JTextField(10);
        playerName.addActionListener(addplayerlistener);
        playerName.getDocument().addDocumentListener(addplayerlistener);
    }

    // EFFECTS: sets up + configures retire player button
    private void setUpRetirePlayerButton() {
        retirePlayerButton = new JButton(retirePlayerString);
        RetirePlayerListener retireplayerlistener = new RetirePlayerListener();
        retirePlayerButton.setActionCommand(retirePlayerString);
        retirePlayerButton.addActionListener(retireplayerlistener);
    }

    // EFFECTS: sets up + configures retire team button
    private void setUpRetireTeamButton() {
        retireTeamButton = new JButton(retireTeamString);
        RetireTeamListener retireteamlistener = new RetireTeamListener();
        retireTeamButton.setActionCommand(retireTeamString);
        retireTeamButton.addActionListener(retireteamlistener);
    }

    // EFFECTS: sets up + configures add team button
    private void setUpAddTeamButton() {
        addTeamButton = new JButton(addTeamString);
        AddTeamListener addteamlistener = new AddTeamListener(addTeamButton);
        addTeamButton.setActionCommand(addTeamString);
        addTeamButton.addActionListener(addteamlistener);
        addTeamButton.setEnabled(false);

        teamName = new JTextField(10);
        teamName.addActionListener(addteamlistener);
        teamName.getDocument().addDocumentListener(addteamlistener);
    }

    // EFFECTS: sets up + configures view players button
    private void setUpViewPlayersButton() {
        viewPlayersButton = new JButton(viewPlayerString);
        ViewPlayersListener viewplayerslistener = new ViewPlayersListener();
        viewPlayersButton.setActionCommand(viewPlayerString);
        viewPlayersButton.addActionListener(viewplayerslistener);
    }

    // EFFECTS: sets up + configures add assist button
    private void setUpAddAssistButton() {
        addAssistButton = new JButton(addAssistString);
        AddAssistListener addassistlistener = new AddAssistListener();
        addAssistButton.setActionCommand(addAssistString);
        addAssistButton.addActionListener(addassistlistener);
    }

    // EFFECTS: sets up + configures add goal button
    private void setUpAddGoalButton() {
        addGoalButton = new JButton(addGoalString);
        AddGoalListener addgoallistener = new AddGoalListener();
        addGoalButton.setActionCommand(addGoalString);
        addGoalButton.addActionListener(addgoallistener);
    }

    // MODIFIES: this
    // EFFECTS: draws buttons + panels to screen + displays to user.
    private void drawGUI(JScrollPane listScrollPane, JScrollPane listScrollPane2) {
        JPanel buttonPane1 = new JPanel();
        buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.LINE_AXIS));
        buttonPane1.add(viewPlayersButton);
        buttonPane1.add(Box.createHorizontalStrut(5));
        buttonPane1.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane1.add(Box.createHorizontalStrut(5));
        buttonPane1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

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

    // MODIFIES: this
    // EFFECTS: removes hockey player from hockey team
    class RetirePlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int indexP = players.getSelectedIndex(); // get selected index (player)
            int indexT = teams.getSelectedIndex(); // get selected index (team)
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team
            HockeyPlayer player = team.getPlayer(indexP); // get selected player
            team.removePlayer(player);

            playersModel.clear(); // reset right pane

            // add all players in team to right pane
            for (int i = 0; i < team.getPlayers(); i++) {
                playersModel.addElement(team.getPlayer(i).toString());
            }

            playSound("./sounds/boo.wav", 0.07); // play appropriate sound effect
        }
    }

    // MODIFIES: this
    // EFFECTS: removes hockey team from list of teams
    class RetireTeamListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int indexT = teams.getSelectedIndex(); // get selected index
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team

            qualifiedTeams.unQualifyTeam(team);

            teamModel.clear(); // reset left pane

            // add all teams to left pane
            for (int i = 0; i < qualifiedTeams.getSize(); i++) {
                teamModel.addElement(qualifiedTeams.getTeam(i).getTeamName());
            }

            playSound("./sounds/boo.wav", 0.07); // play appropriate sound effect
        }
    }

    // MODIFIES: this
    // EFFECTS: add goal to player depending on user index
    class AddGoalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int indexP = players.getSelectedIndex(); // get selected index (player)
            int indexT = teams.getSelectedIndex(); // get selected index (team)
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team
            HockeyPlayer player = team.getPlayer(indexP); // get selected player
            player.addGoal(); // add goal to player

            playersModel.clear(); // reset right pane

            // add all players in team to right pane
            for (int i = 0; i < team.getPlayers(); i++) {
                playersModel.addElement(team.getPlayer(i).toString());
            }

            playSound("./sounds/goal.wav", 0.07); // play appropriate sound effect
        }
    }

    // MODIFIES: this
    // EFFECTS: add assist to player depending on user index
    class AddAssistListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int indexP = players.getSelectedIndex(); // get selected index (player)
            int indexT = teams.getSelectedIndex(); // get selected index (team)
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team
            HockeyPlayer player = team.getPlayer(indexP); // get selected player
            player.addAssist(); // add goal to player

            playersModel.clear(); // reset right pane

            // add all players in team to right pane
            for (int i = 0; i < team.getPlayers(); i++) {
                playersModel.addElement(team.getPlayer(i).toString());
            }

            playSound("./sounds/goal.wav", 0.07); // play appropriate sound effect
        }
    }

    // MODIFIES: this
    // EFFECTS: configures view players button - displays all players on
    //          hockey team to right panel depending on user index
    class ViewPlayersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = teams.getSelectedIndex(); // get selected index
            HockeyTeam selected = qualifiedTeams.getTeam(index); // get selected team
            playersModel.clear(); // reset right pane

            // add all players in team to right pane
            for (int i = 0; i < selected.getPlayers(); i++) {
                playersModel.addElement(selected.getPlayer(i).toString());
            }

            playSound("./sounds/wow.wav", 0.07); // play appropriate sound effect
        }
    }

    // MODIFIES: this
    // EFFECTS: configures add player button - create new hockey player with
    //          input name, adds to right panel
    class AddPlayerListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private final JButton button;

        public AddPlayerListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = playerName.getText(); // get user input
            int index = teams.getSelectedIndex(); // get selected index
            HockeyTeam selected = qualifiedTeams.getTeam(index); // get selected team
            playersModel.clear(); // reset right pane
            HockeyPlayer hockeyplayer = new HockeyPlayer(name, 0,0); // create new player
            selected.addPlayer(hockeyplayer); // add player to team

            // add all players in team to right pane
            for (int i = 0; i < selected.getPlayers(); i++) {
                playersModel.addElement(selected.getPlayer(i).toString());
            }

            // reset the text field.
            playerName.requestFocusInWindow();
            playerName.setText("");

            playSound("./sounds/applause.wav", 0.10); // play appropriate sound effect
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //EFFECTS: enables add player button if it isn't already
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //EFFECTS: if user input is empty, disable add player button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: configures add team button - creates new hockey team
    //          with input name, adds to left panel
    class AddTeamListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private final JButton button;

        public AddTeamListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = teamName.getText(); // get user input

            HockeyTeam hockeyTeam = new HockeyTeam(name, 0, 0); // create new team
            qualifiedTeams.qualifyTeam(hockeyTeam); // add team to list of teams
            teamModel.addElement(teamName.getText()); // add team to left pane

            // reset the text field.
            teamName.requestFocusInWindow();
            teamName.setText("");

            playSound("./sounds/cheer.wav", 0.07); // play appropriate sound effect
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //EFFECTS: enables add team button if it isn't already
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //EFFECTS: if user input is empty, disable add team button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //Required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            //No selection, disable button.
            //Selection, enable button.
            viewPlayersButton.setEnabled(teams.getSelectedIndex() != -1);
        }
    }

    // MODIFIES: this
    // EFFECTS: create the hockey application GUI and display it
    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // opens load GUI when GUI is first run:
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                confirmAndOpen();
            }
        });

        // opens save GUI when GUI is exited:
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
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

    // EFFECTS: starts GUI
    public static void main(String[] args) {
        createAndShowGUI();
    }
}

