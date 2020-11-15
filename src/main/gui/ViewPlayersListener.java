package gui;

import model.HockeyTeam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teams;
import static gui.HockeyGUI.qualifiedTeams;
import static gui.HockeyGUI.playersModel;
import static gui.HockeyGUI.playSound;

// Configures view player button
class ViewPlayersListener implements ActionListener {

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: configures view players button - displays all players on
    //          hockey team to right panel depending on user index
    public void actionPerformed(ActionEvent e) {
        int index = teams.getSelectedIndex(); // get selected index
        HockeyTeam selected = qualifiedTeams.getTeam(index); // get selected team
        playersModel.clear(); // reset right pane

        // add all players in team to right pane
        for (int i = 0; i < selected.getTeamSize(); i++) {
            playersModel.addElement(selected.getPlayer(i).toString());
        }

        playSound("./sounds/wow.wav", 0.07); // play appropriate sound effect
    }
}