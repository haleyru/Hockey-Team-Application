package model;



// Represents a hockey player having a name and a number of goals, assists, and points.
public class HockeyPlayer {
    private String name;                     // Player's name
    private int goals;                       // Player's goals
    private int assists;                     // Player's assists
    private int points;                      // Player's points (goals + assists)


    // EFFECTS: construct a hockey player with a name, initial goals/assists & points.
    public HockeyPlayer(String name, int goals, int assists) {
        this.name = name;
        this.goals = goals;
        this.assists = assists;
        this.points = goals + assists;
    }

    // EFFECTS: return name of hockey player
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
    // EFFECTS: adds a goal to hockey player + updates points accordingly
    public void addGoal() {
        goals++;
        points++;
    }

    // MODIFIES: this
    // EFFECTS: adds an assist to hockey player + updates points accordingly
    public void addAssist() {
        assists++;
        points++;
    }

    // EFFECTS: returns a string representation of a hockey player
    public String toString() {
        return "[Name = " + name + ", Points = " + points + ", Goals = " + goals + ", Assists = " + assists + "]";
    }
}
