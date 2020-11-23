package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents single hockey player with name, number of goals, assists, and points.
public class HockeyPlayer implements Writable {
    private final String name;                     // Player name
    private int goals;                       // Player goals
    private int assists;                     // Player assists
    private int points;                      // Player points (goals + assists)


    // EFFECTS: constructs single hockey player with name, initial goals/assists & points.
    public HockeyPlayer(String name, int goals, int assists) {
        this.name = name;
        this.goals = goals;
        this.assists = assists;
        this.points = goals + assists;
    }

    // EFFECTS: returns name of hockey player
    public String getName() {
        return name;
    }

    // EFFECTS: returns total goals scored
    public int getGoals() {
        return goals;
    }

    // EFFECTS: returns total assists
    public int getAssists() {
        return assists;
    }

    // EFFECTS: returns total points
    public int getPoints() {
        return points;
    }

    // MODIFIES: this
    // EFFECTS: adds single goal to hockey player + updates points
    public void addGoal() {
        goals++;
        points++;
    }

    // MODIFIES: this
    // EFFECTS: adds assist to hockey player + updates points
    public void addAssist() {
        assists++;
        points++;
    }

    // EFFECTS: returns string representation of a hockey player
    public String toString() {
        return "[Name = " + name + ", Points = " + points + ", Goals = " + goals + ", Assists = " + assists + "]";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player name", name);
        json.put("goals", goals);
        json.put("assists", assists);
        json.put("points", points);
        return json;
    }
}
