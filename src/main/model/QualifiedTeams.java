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

    // EFFECTS: adds hockey team to list of qualified hockey teams
    public void qualifyTeam(HockeyTeam t) {
        teams.add(t);
    }

    // REQUIRES: hockey team is qualified
    // EFFECTS: removes hockey team from list of qualified hockey teams
    public void unQualifyTeam(HockeyTeam t) {
        teams.remove(t);
    }

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
}
