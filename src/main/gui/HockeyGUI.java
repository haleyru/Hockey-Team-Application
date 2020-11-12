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
import javax.swing.*;
import javax.swing.event.*;

// Hockey Team Application GUI
// ** Borrows features from ListDemo.java from oracle
public class HockeyGUI extends JPanel implements ListSelectionListener {
    public static QualifiedTeams qualifiedTeams;
    private JList teams;
    private JList players;
    private DefaultListModel teamModel;
    private DefaultListModel playersModel;
    private static final String addTeamString = "Add Team";
    private static final String addPlayerString = "Add Player";
    private static final String viewPlayerString = "View Players";
    private JButton addTeamButton;
    private JButton addPlayerButton;
    private JButton viewPlayersButton;
    private JTextField teamName;
    private JTextField playerName;
    private static boolean load;
    public static JFrame frame = new JFrame("Hockey Team Application");
    private static final String JSON_STORE = "./data/hockeyTeams.json";
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    // EFFECTS: loads + runs hockey application
    public HockeyGUI() {
        super(new BorderLayout());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        if (load) {
            try {
                qualifiedTeams = jsonReader.read();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        } else {
            qualifiedTeams = new QualifiedTeams();
        }

        JScrollPane listScrollPane = setUpTeams(qualifiedTeams);
        JScrollPane listScrollPane2 = setUpPlayers();
        setUpButtons();
        drawGUI(listScrollPane, listScrollPane2);
    }

    // MODIFIES: this
    // EFFECTS: sets up player scroll panel
    private JScrollPane setUpPlayers() {
        playersModel = new DefaultListModel();
        players = new JList(playersModel);
        players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        players.setSelectedIndex(0);
        players.addListSelectionListener(this);
        JScrollPane listScrollPane2 = new JScrollPane(players);
        return listScrollPane2;
    }

    // MODIFIES: this
    // EFFECTS: sets up team scroll panel
    private JScrollPane setUpTeams(QualifiedTeams qualified) {
        teamModel = new DefaultListModel();
        for (HockeyTeam t : qualified.getTeams()) {
            teamModel.addElement(t.getTeamName());
        }
        teams = new JList(teamModel);
        teams.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teams.setSelectedIndex(0);
        teams.addListSelectionListener(this);
        teams.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(teams);
        return listScrollPane;
    }

    // MODOFIES: this
    // EFFECTS: sets up + configures all buttons
    private void setUpButtons() {
        viewPlayersButton = new JButton(viewPlayerString);
        ViewPlayersListener viewplayerslistener = new ViewPlayersListener();
        viewPlayersButton.setActionCommand(viewPlayerString);
        viewPlayersButton.addActionListener(viewplayerslistener);

        addTeamButton = new JButton(addTeamString);
        AddTeamListener addteamlistener = new AddTeamListener(addTeamButton);
        addTeamButton.setActionCommand(addTeamString);
        addTeamButton.addActionListener(addteamlistener);
        addTeamButton.setEnabled(false);

        addPlayerButton = new JButton(addPlayerString);
        AddPlayerListener addplayerlistener = new AddPlayerListener(addPlayerButton);
        addPlayerButton.setActionCommand(addPlayerString);
        addPlayerButton.addActionListener(addplayerlistener);
        addPlayerButton.setEnabled(false);

        teamName = new JTextField(10);
        teamName.addActionListener(addteamlistener);
        teamName.getDocument().addDocumentListener(addteamlistener);

        playerName = new JTextField(10);
        playerName.addActionListener(addplayerlistener);
        playerName.getDocument().addDocumentListener(addplayerlistener);
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

        add(listScrollPane, BorderLayout.WEST);
        add(listScrollPane2, BorderLayout.EAST);
        add(buttonPane1, BorderLayout.BEFORE_FIRST_LINE);
        add(buttonPane2, BorderLayout.AFTER_LAST_LINE);
    }

    // MODIFIES: this
    // EFFECTS: configures add player button - create new hockey player with
    //          input name, adds to hockey player panel
    class AddPlayerListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddPlayerListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = playerName.getText();
            int index = teams.getSelectedIndex();
            HockeyTeam selected = qualifiedTeams.getTeam(index);
            playersModel.clear();
            HockeyPlayer hockeyplayer = new HockeyPlayer(name, 0,0);
            HockeyTeam hockeyteam = qualifiedTeams.getTeam(index);
            hockeyteam.addPlayer(hockeyplayer);

            for (int i = 0; i < selected.getPlayers(); i++) {
                playersModel.addElement(selected.getPlayer(i).toString());
            }

            //Reset the text field.
            playerName.requestFocusInWindow();
            playerName.setText("");

            //Select the new item and make it visible.
            teams.setSelectedIndex(index);
            teams.ensureIndexIsVisible(index);
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

        //Required by DocumentListener.
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    class ViewPlayersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = teams.getSelectedIndex();
            HockeyTeam selected = qualifiedTeams.getTeam(index);
            playersModel.clear();

            for (int i = 0; i < selected.getPlayers(); i++) {
                playersModel.addElement(selected.getPlayer(i).toString());
            }

            teams.setSelectedIndex(index);
            teams.ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: configures add team button - creates new hockey team
    //          with input name, adds to hockey team panel
    class AddTeamListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddTeamListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = teamName.getText();

            int index = teams.getSelectedIndex();

            HockeyTeam hockeyTeam = new HockeyTeam(name, 0, 0);
            qualifiedTeams.qualifyTeam(hockeyTeam);
            teamModel.addElement(teamName.getText());

            teamName.requestFocusInWindow();
            teamName.setText("");

            teams.setSelectedIndex(index);
            teams.ensureIndexIsVisible(index);
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

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (teams.getSelectedIndex() == -1) {
                //No selection, disable button.
                viewPlayersButton.setEnabled(false);

            } else {
                //Selection, enable button.
                viewPlayersButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: create the hockey application GUI and display it
    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                confirmAndOpen();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
            }
        });

        JComponent newContentPane = new HockeyGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: saves data to file depending on user input + closes GUI
    public static void confirmAndExit() {
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
    // EFFECTS: saves data to file depending on user input + closes GUI
    public static void confirmAndOpen() {
        if (JOptionPane.showConfirmDialog(
                frame,
                "Load data before opening?",
                "Please confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
        ) {
            load = true;
        } else {
            load = false;
        }
        JComponent newContentPane = new HockeyGUI();
        frame.setContentPane(newContentPane);
        frame.pack();
    }

    // EFFECTS: starts GUI
    public static void main(String[] args) {
        createAndShowGUI();
    }
}

