package gui;

import model.HockeyPlayer;
import model.HockeyTeam;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teams;
import static gui.HockeyGUI.playersModel;
import static gui.HockeyGUI.qualifiedTeams;
import static gui.HockeyGUI.playerName;
import static gui.HockeyGUI.playSound;

// Configures add player button
class AddPlayerListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private final JButton button;

    public AddPlayerListener(JButton button) {
        this.button = button;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: configures add player button - create new hockey player with
    //          input name, adds to right panel
    public void actionPerformed(ActionEvent e) {
        String name = playerName.getText(); // get user input
        int index = teams.getSelectedIndex(); // get selected index
        HockeyTeam selected = qualifiedTeams.getTeam(index); // get selected team
        playersModel.clear(); // reset right pane
        HockeyPlayer hockeyplayer = new HockeyPlayer(name, 0,0); // create new player
        selected.addPlayer(hockeyplayer); // add player to team

        // add all players in team to right pane
        for (int i = 0; i < selected.getTeamSize(); i++) {
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

    // EFFECTS: enables add player button if it isn't already
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // EFFECTS: if user input is empty, disable add player button
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
