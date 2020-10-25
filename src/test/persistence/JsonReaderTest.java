package persistence;

import model.HockeyTeam;
import model.QualifiedTeams;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// ** Uses parts of the JsonSerializationDemo program **
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QualifiedTeams teams = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeams() {
        JsonReader reader = new JsonReader("./data/testEmptyTeams.json");
        try {
            QualifiedTeams teams = reader.read();
            assertEquals(0, teams.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyPlayers() {
        JsonReader reader = new JsonReader("./data/testEmptyPlayers.json");
        try {
            QualifiedTeams teams = reader.read();
            assertEquals(3, teams.getSize());
            List<HockeyTeam> allTeams = teams.getTeams();

            checkTeam("The A Team", 100, 0, allTeams.get(0));
            checkTeam("The B Team", 10, 0, allTeams.get(1));
            checkTeam("The C Team", 0, 0, allTeams.get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeams() {
        JsonReader reader = new JsonReader("./data/testGeneralTeams.json");
        try {
            QualifiedTeams teams = reader.read();
            assertEquals(2, teams.getSize());
            List<HockeyTeam> allTeams = teams.getTeams();

            checkTeam("Vancouver Canucks", 36, 27, allTeams.get(0));
            checkTeam("Calgary Flames", 36, 27, allTeams.get(1));

            checkPlayer("J. Miller", 27, 45, allTeams.get(0).getPlayer(0));
            checkPlayer("J. Beagle", 2, 6, allTeams.get(0).getPlayer(1));
            checkPlayer("A. Edler", 5, 28, allTeams.get(0).getPlayer(2));

            checkPlayer("M. Tkachuk", 23, 38, allTeams.get(1).getPlayer(0));
            checkPlayer("D. Ryan", 10, 19, allTeams.get(1).getPlayer(1));
            checkPlayer("M. Frolik", 5, 5, allTeams.get(1).getPlayer(2));

            assertEquals(3, allTeams.get(0).getTeamSize());
            assertEquals(3, allTeams.get(1).getTeamSize());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
