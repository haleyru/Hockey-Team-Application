package persistence;

import model.HockeyPlayer;
import model.HockeyTeam;
import model.QualifiedTeams;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// ** Uses parts of the JsonSerializationDemo program **
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            QualifiedTeams teams = new QualifiedTeams();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTeams() {
        try {
            QualifiedTeams teams = new QualifiedTeams();
            JsonWriter writer = new JsonWriter("./data/testEmptyTeams.json");
            writer.open();
            writer.write(teams);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyTeams.json");
            teams = reader.read();
            assertEquals(0, teams.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlayers() {
        try {
            QualifiedTeams teams = new QualifiedTeams();
            HockeyTeam A = new HockeyTeam("The A Team", 100, 0);
            HockeyTeam B = new HockeyTeam("The B Team", 10, 0);
            HockeyTeam C = new HockeyTeam("The C Team", 0, 0);
            teams.qualifyTeam(A);
            teams.qualifyTeam(B);
            teams.qualifyTeam(C);

            assertEquals(3, teams.getSize());

            JsonWriter writer = new JsonWriter("./data/testEmptyPlayers.json");
            writer.open();
            writer.write(teams);
            writer.close();
            JsonReader reader = new JsonReader("./data/testEmptyPlayers.json");

            teams = reader.read();
            List<HockeyTeam> allTeams = teams.getTeams();

            checkTeam("The A Team", 100, 0, allTeams.get(0));
            checkTeam("The B Team", 10, 0, allTeams.get(1));
            checkTeam("The C Team", 0, 0, allTeams.get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterGeneralWorkRoom() {
        try {
            QualifiedTeams teams = new QualifiedTeams();

            HockeyTeam van = new HockeyTeam("Vancouver Canucks", 36, 27);
            HockeyTeam cal = new HockeyTeam("Calgary Flames", 36, 27);
            teams.qualifyTeam(van);
            teams.qualifyTeam(cal);
            assertEquals(2, teams.getSize());

            HockeyPlayer vanFirst = new HockeyPlayer("J. Miller", 27, 45);
            HockeyPlayer vanSecond = new HockeyPlayer("J. Beagle", 2, 6);
            HockeyPlayer vanThird = new HockeyPlayer("A. Edler", 5, 28);
            HockeyPlayer calFirst = new HockeyPlayer("M. Tkachuk", 23, 38);
            HockeyPlayer calSecond = new HockeyPlayer("D. Ryan", 10, 19);
            HockeyPlayer calThird = new HockeyPlayer("M. Frolik", 5, 5);
            van.addPlayer(vanFirst);
            van.addPlayer(vanSecond);
            van.addPlayer(vanThird);
            cal.addPlayer(calFirst);
            cal.addPlayer(calSecond);
            cal.addPlayer(calThird);

            assertEquals(3, van.getTeamSize());
            assertEquals(3, cal.getTeamSize());

            JsonWriter writer = new JsonWriter("./data/testGeneralTeams.json");
            writer.open();
            writer.write(teams);
            writer.close();
            JsonReader reader = new JsonReader("./data/testGeneralTeams.json");

            teams = reader.read();
            List<HockeyTeam> allTeams = teams.getTeams();

            checkTeam("Vancouver Canucks", 36, 27, allTeams.get(0));
            checkTeam("Calgary Flames", 36, 27, allTeams.get(1));

            checkPlayer("J. Miller", 27, 45, allTeams.get(0).getPlayer(0));
            checkPlayer("J. Beagle", 2, 6, allTeams.get(0).getPlayer(1));
            checkPlayer("A. Edler", 5, 28, allTeams.get(0).getPlayer(2));
            checkPlayer("M. Tkachuk", 23, 38, allTeams.get(1).getPlayer(0));
            checkPlayer("D. Ryan", 10, 19, allTeams.get(1).getPlayer(1));
            checkPlayer("M. Frolik", 5, 5, allTeams.get(1).getPlayer(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
