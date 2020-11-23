package gui;

import exceptions.TeamAlreadyExistsException;
import model.HockeyTeam;
import model.QualifiedTeams;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teamName;
import static gui.HockeyGUI.teamModel;
import static gui.HockeyGUI.playSound;

// Configures add team button
class AddTeamListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private final JButton button;
    private QualifiedTeams qualifiedTeams;

    public AddTeamListener(JButton button, QualifiedTeams qualifiedTeams) {
        this.button = button;
        this.qualifiedTeams = qualifiedTeams;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: configures add team button - creates new hockey team
    //          with input name, adds to left panel
    public void actionPerformed(ActionEvent e) {
        String name = teamName.getText(); // get user input
        HockeyTeam hockeyTeam = new HockeyTeam(name, 0, 0); // create new team

        try {
            qualifiedTeams.qualifyTeam(hockeyTeam); // add team to list of teams
            teamModel.addElement(teamName.getText()); // add team to left pane
            playSound("./sounds/cheer.wav", 0.07);
        } catch (TeamAlreadyExistsException ex) {
            playSound("./sounds/error.wav", 0.07);
        } finally {

            // reset the text field.
            teamName.requestFocusInWindow();
            teamName.setText("");
        }
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


