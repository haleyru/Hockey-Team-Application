package gui;

import model.HockeyTeam;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teamName;
import static gui.HockeyGUI.qualifiedTeams;
import static gui.HockeyGUI.teamModel;
import static gui.HockeyGUI.playSound;

// Configures add team button
class AddTeamListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private final JButton button;

    public AddTeamListener(JButton button) {
        this.button = button;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: configures add team button - creates new hockey team
    //          with input name, adds to left panel
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


