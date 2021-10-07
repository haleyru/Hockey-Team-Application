package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents single hockey player with name, number of goals, assists, and points.
public class HockeyPlayer2 extends HockeyPlayer implements Writable {

    private int goals;                       // Player goals
    private int assists;                     // Player assists
    private int points;                      // Player points (goals + assists)


    // EFFECTS: constructs single hockey player with name, initial goals/assists & points.
    public HockeyPlayer2(String name, int goals, int assists) {
        super(name, goals, assists);
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
}





