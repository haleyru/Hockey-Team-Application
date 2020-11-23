package gui;

import model.HockeyTeam;
import model.QualifiedTeams;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teams;
import static gui.HockeyGUI.playersModel;
import static gui.HockeyGUI.playSound;

// Configures view player button
class ViewPlayersListener implements ActionListener {
    private QualifiedTeams qualifiedTeams;

    public ViewPlayersListener(QualifiedTeams qualifiedTeams) {
        this.qualifiedTeams = qualifiedTeams;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: configures view players button - displays all players on
    //          hockey team to right panel depending on user index
    public void actionPerformed(ActionEvent e) {
        try {
            int index = teams.getSelectedIndex(); // get selected index
            HockeyTeam selected = qualifiedTeams.getTeam(index); // get selected team
            playersModel.clear(); // reset right pane

            if (selected.getTeamSize() > 0) {
                // add all players in team to right pane
                for (int i = 0; i < selected.getTeamSize(); i++) {
                    playersModel.addElement(selected.getPlayer(i).toString());
                }

                playSound("./sounds/wow.wav", 0.07); // play appropriate sound effect
            } else {
                playSound("./sounds/error.wav", 0.07); // play appropriate sound effect
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            playSound("./sounds/error.wav", 0.07); // play appropriate sound effect
        }
    }
}