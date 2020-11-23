package gui;

import model.HockeyTeam;
import model.QualifiedTeams;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.HockeyGUI.teamModel;
import static gui.HockeyGUI.playersModel;
import static gui.HockeyGUI.teams;
import static gui.HockeyGUI.playSound;

// Configures retire team button
class RetireTeamListener implements ActionListener {
    private final QualifiedTeams qualifiedTeams;

    public RetireTeamListener(QualifiedTeams qualifiedTeams) {
        this.qualifiedTeams = qualifiedTeams;
    }

    // Required by ActionListener.
    // MODIFIES: this
    // EFFECTS: removes hockey team from list of teams + removes from left pane
    public void actionPerformed(ActionEvent e) {
        try {
            int indexT = teams.getSelectedIndex(); // get selected index
            HockeyTeam team = qualifiedTeams.getTeam(indexT); // get selected team
            qualifiedTeams.unQualifyTeam(team);
            teamModel.clear(); // reset left pane

            // add all teams to left pane
            for (int i = 0; i < qualifiedTeams.getSize(); i++) {
                teamModel.addElement(qualifiedTeams.getTeam(i).getTeamName());
            }

            playersModel.clear(); // reset right pane

            playSound("./sounds/boo.wav", 0.07); // play appropriate sound effect
        } catch (ArrayIndexOutOfBoundsException ex) {
            playSound("./sounds/error.wav", 0.07); // play appropriate sound effect
        }
    }
}

