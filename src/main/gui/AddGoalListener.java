package gui;

import model.HockeyPlayer;
import model.HockeyTeam;
import model.QualifiedTeams;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.players;
import static gui.HockeyGUI.playersModel;
import static gui.HockeyGUI.teams;

import static gui.HockeyGUI.playSound;

// Configures add goal button
class AddGoalListener implements ActionListener {
    private QualifiedTeams qualifiedTeams;

    public AddGoalListener(QualifiedTeams qualifiedTeams) {
        this.qualifiedTeams = qualifiedTeams;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: add goal to player depending on user index
    public void actionPerformed(ActionEvent e) {
        try {
            int indexP = players.getSelectedIndex(); // get selected index (player)
            int indexT = teams.getSelectedIndex(); // get selected index (team)
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team
            HockeyPlayer player = team.getPlayer(indexP); // get selected player
            player.addGoal(); // add goal to player
            playersModel.clear(); // reset right pane

            // add all players in team to right pane
            for (int i = 0; i < team.getTeamSize(); i++) {
                playersModel.addElement(team.getPlayer(i).toString());
            }

            playSound("./sounds/goal.wav", 0.07); // play appropriate sound effect
        } catch (ArrayIndexOutOfBoundsException ex) {
            playSound("./sounds/error.wav", 0.07); // play appropriate sound effect
        }
    }
}
