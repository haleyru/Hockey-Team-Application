package model;

import java.util.ArrayList;

// Represents a hockey team having a name, a number of wins, losses, and total games played.
public class HockeyTeam {
    private String teamname;                     // Name of hockey team
    private int gamesplayed;                     // Total games played this season
    private int wins;                            // Total wins this season
    private int losses;                          // Total losses this season
    private ArrayList<HockeyPlayer> team;        // List of hockey players on team

    // EFFECTS: constructs an empty list of hockey players, total wins and losses and total games played
    public HockeyTeam(String name, int wins, int losses) {
        teamname = name;
        team = new ArrayList<>();
        gamesplayed = wins + losses;
        this.wins = wins;
        this.losses = losses;
    }

    // EFFECTS: returns name of the hockey team
    public String getTeamName() {
        return teamname;
    }

    // EFFECTS: returns total number of wins
    public int getWins() {
        return wins;
    }

    // EFFECTS: returns total number of losses
    public int getLosses() {
        return losses;
    }

    // EFFECTS: returns total number of games played
    public int getGamesPlayed() {
        return gamesplayed;
    }

    // EFFECTS: returns total number of players on hockey team
    public int getTeamSize() {
        return team.size();
    }

    // REQUIRES: hockey player isn't already on hockey team
    // MODIFIES: this
    // EFFECTS: adds hockey player to hockey team
    public void addPlayer(HockeyPlayer p) {
        team.add(p);
    }

    // REQUIRES: player is already on hockey team
    // MODIFIES: this
    // EFFECTS: removes hockey player from hockey team
    public void removePlayer(HockeyPlayer p) {
        team.remove(p);
    }

    // MODIFIES: this
    // EFFECTS: adds single win to hockey team and updates total games played
    public void addWin() {
        wins++;
        gamesplayed++;
    }

    // MODIFIES: this
    // EFFECTS: adds single loss to hockey team and updates total games played
    public void addLoss() {
        losses++;
        gamesplayed++;
    }

    // EFFECTS: returns a string representation of all hockey player names in hockey team
    public String playerList() {
        ArrayList<String> players = new ArrayList<>();
        for (HockeyPlayer p : team) {
            players.add(p.getName());
        }
        return "Players = " + players + "";
    }
}




