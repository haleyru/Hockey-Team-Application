package model;

import exceptions.TeamAlreadyExistsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of hockey teams qualified to play this season
public class QualifiedTeams implements Writable {
    private final ArrayList<HockeyTeam> teams;

    // EFFECTS: construct empty list of qualified hockey teams
    public QualifiedTeams() {
        teams = new ArrayList<>();
    }

    // EFFECTS: return total number of hockey teams qualified
    public int getSize() {
        return teams.size();
    }

    // EFFECTS: return hockey team at index position i in team list
    public HockeyTeam getTeam(int i) {
        return teams.get(i);
    }

    // EFFECTS: return all qualified hockey teams
    public ArrayList<HockeyTeam> getTeams() {
        return teams;
    }

    // MODIFIES: this
    // EFFECTS: if hockey team is not added already, add hockey team to list of qualified hockey teams;
    //          otherwise throw TeamAlreadyExists exception.
    public void qualifyTeam(HockeyTeam t) throws TeamAlreadyExistsException {
        if (!teams.contains(t)) {
            teams.add(t);
        } else {
            throw new TeamAlreadyExistsException();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove hockey team from list of qualified hockey teams
    public void unQualifyTeam(HockeyTeam t) {
        teams.remove(t);
    }

    // EFFECTS: return string representation of all qualified hockey team names
    public String teamList() {
        ArrayList<String> teamList = new ArrayList<>();
        for (HockeyTeam t : teams) {
            teamList.add(t.getTeamName());
        }
        return "Teams = " + teamList + "";
    }

    // EFFECTS: return top team based on wins and number of games played
    public HockeyTeam topTeam() {
        double n = 0;
        HockeyTeam top = null;
        for (HockeyTeam t : teams) {
            double score = (double)t.getWins() / t.getGamesPlayed();
            if (score > n) {
                top = t;
                n = score;
            }
        }
        return top;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns all qualified teams as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (HockeyTeam t : teams) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

}


