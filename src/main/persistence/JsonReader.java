package persistence;

import exceptions.TeamAlreadyExistsException;
import model.HockeyPlayer;
import model.QualifiedTeams;
import model.HockeyTeam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// ** Uses parts of the JsonSerializationDemo program **
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads qualified teams from file and returns it;
    // throws IOException if an error occurs reading data from file
    public QualifiedTeams read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses qualified teams from JSON object and returns it
    private QualifiedTeams parseWorkRoom(JSONObject jsonObject) {
        QualifiedTeams teams = new QualifiedTeams();
        addTeams(teams, jsonObject);
        return teams;
    }

    // MODIFIES: teams
    // EFFECTS: parses teams from JSON object and adds them to qualified teams
    private void addTeams(QualifiedTeams teams, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(teams, nextTeam);
        }
    }

    // MODIFIES: teams
    // EFFECTS: parses players from JSON object and adds it to qualified teams
    private void addTeam(QualifiedTeams teams, JSONObject jsonObject) {
        String name = jsonObject.getString("team name");
        int wins = jsonObject.getInt("wins");
        int losses = jsonObject.getInt("losses");
        HockeyTeam team = new HockeyTeam(name, wins, losses);
        try {
            teams.qualifyTeam(team);
        } catch (TeamAlreadyExistsException e) {
            System.out.println("Team already exists");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: teams
    // EFFECTS: parses player from JSON object and adds it to qualified teams
    private void addPlayer(HockeyTeam team, JSONObject jsonObject) {
        String name = jsonObject.getString("player name");
        int goals = jsonObject.getInt("goals");
        int assists = jsonObject.getInt("assists");
        HockeyPlayer player = new HockeyPlayer(name, goals, assists);
        team.addPlayer(player);
    }
}
