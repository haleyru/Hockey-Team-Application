package persistence;

import model.HockeyPlayer;
import model.HockeyTeam;

import static org.junit.jupiter.api.Assertions.assertEquals;

// ** Uses parts of the JsonSerializationDemo program **
public class JsonTest {
    protected void checkTeam(String name, int wins, int losses, HockeyTeam team) {
        assertEquals(name, team.getTeamName());
        assertEquals(wins, team.getWins());
        assertEquals(wins + losses, team.getGamesPlayed());
        assertEquals(losses, team.getLosses());
    }

    protected void checkPlayer(String name, int goals, int assists, HockeyPlayer player) {
        assertEquals(name, player.getName());
        assertEquals(goals, player.getGoals());
        assertEquals(assists, player.getAssists());
        assertEquals(goals + assists, player.getPoints());
    }

}

