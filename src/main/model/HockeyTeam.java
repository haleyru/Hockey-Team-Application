package model;

import java.util.ArrayList;

// Represents a hockey team having a name, a number of wins, losses, and total games played.
public class HockeyTeam {
    private String teamName;                     // Name of hockey team
    private int gamesPlayed;                     // Total games played this season
    private int wins;                            // Total wins this season
    private int losses;                          // Total losses this season
    private ArrayList<HockeyPlayer> team;        // List of hockey players on team

    // EFFECTS: construct an empty list of hockey players, total wins and losses and total games played
    public HockeyTeam(String name, int wins, int losses) {
        teamName = name;
        team = new ArrayList<>();
        gamesPlayed = wins + losses;
        this.wins = wins;
        this.losses = losses;
    }

    // EFFECTS: return name of hockey team
    public String getTeamName() {
        return teamName;
    }

    // EFFECTS: return total number of wins
    public int getWins() {
        return wins;
    }

    // EFFECTS: return total number of losses
    public int getLosses() {
        return losses;
    }

    // EFFECTS: return total number of games played
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // EFFECTS: return total number of players on hockey team
    public int getTeamSize() {
        return team.size();
    }

    // EFFECTS: return hockey player at index position i in team list
    public HockeyPlayer getPlayer(int i) {
        return team.get(i);
    }

    // MODIFIES: this
    // EFFECTS: add hockey player to hockey team
    public void addPlayer(HockeyPlayer p) {
        if (!team.contains(p)) {
            team.add(p);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove hockey player from hockey team
    public void removePlayer(HockeyPlayer p) {
        team.remove(p);
    }


    // MODIFIES: this
    // EFFECTS: add single win to hockey team and update total games played
    public void addWin() {
        wins++;
        gamesPlayed++;
    }

    // MODIFIES: this
    // EFFECTS: add single loss to hockey team and update total games played
    public void addLoss() {
        losses++;
        gamesPlayed++;
    }

    // EFFECTS: return string representation of a hockey team player list
    public String playerList() {
        ArrayList<String> players = new ArrayList<>();
        for (HockeyPlayer p : team) {
            players.add(p.getName());
        }
        return "Players = " + players + "";
    }

    // EFFECTS: return string representation of team name, wins, losses, total games played
    public String teamStats() {
        return "[Team name = " + teamName + ", Wins = " + wins + ", Losses = " + losses + ", Games played = "
                + gamesPlayed + "]";
    }

    // EFFECTS: return top player in team based on points
    public HockeyPlayer topPlayer() {
        int n = 0;
        HockeyPlayer top = null;
        for (HockeyPlayer p : team) {
            if (p.getPoints() > n) {
                top = p;
                n = p.getPoints();
            }
        }
        return top;
    }
}




