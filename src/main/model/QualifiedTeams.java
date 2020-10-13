package model;

import java.util.ArrayList;

// Represents a list of hockey teams qualified for the NHL
public class QualifiedTeams {
    private ArrayList<HockeyTeam> teams;

    // EFFECTS: constructs an empty list of qualified hockey teams
    public QualifiedTeams() {
        teams = new ArrayList<>();
    }

    // EFFECTS: returns number of hockey teams qualified
    public int getSize() {
        return teams.size();
    }

    // REQUIRES: hockey team isn't qualified
    // MODIFIES: this
    // EFFECTS: adds hockey team to list of qualified hockey teams
    public void qualifyTeam(HockeyTeam t) {
        teams.add(t);
    }

    // REQUIRES: hockey team is qualified
    // MODIFIES: this
    // EFFECTS: removes hockey team from list of qualified hockey teams
    public void unQualifyTeam(HockeyTeam t) {
        teams.remove(t);
    }

    // REQUIRES: at least one hockey team is qualified
    // EFFECTS: returns hockey team with most wins
    public HockeyTeam topTeam() {
        int n = 0;
        HockeyTeam top = null;
        for (HockeyTeam t : teams) {
            if (t.getWins() > n) {
                top = t;
                n = t.getWins();
            }
        }
        return top;
    }

    // EFFECTS: returns a string representation of all hockey teams qualified
    public String teamList() {
        ArrayList<String> teamlist = new ArrayList<>();
        for (HockeyTeam t : teams) {
            teamlist.add(t.getTeamName());
        }
        return "Teams = " + teamlist + "";
    }
}


