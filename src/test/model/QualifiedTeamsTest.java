package model;

import exceptions.TeamAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class QualifiedTeamsTest {

    @Test
    public void testQualifyTeamNoException() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        QualifiedTeams qualified = new QualifiedTeams();
        try {
            qualified.qualifyTeam(team1);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }
        assertEquals(1,qualified.getSize());

        HockeyTeam team2 = new HockeyTeam("Boomers", 3, 2);
        try {
            qualified.qualifyTeam(team2);
            assertEquals(2,qualified.getSize());
        } catch (TeamAlreadyExistsException e) {
            fail();
        }
    }

    @Test
    public void testQualifyTeamExceptionThrown() {
        QualifiedTeams qualified = new QualifiedTeams();
        HockeyTeam team1 = new HockeyTeam("Zoomers", 3, 3);
        HockeyTeam team2 = new HockeyTeam("Zoomers", 6, 0);

        try {
            qualified.qualifyTeam(team1);
            qualified.qualifyTeam(team2);
            fail();
        } catch (TeamAlreadyExistsException e) {
            // expected
        }
    }

    @Test
    public void testUnQualifyTeam() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        QualifiedTeams qualified = new QualifiedTeams();

        try {
            qualified.qualifyTeam(team1);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }

        assertEquals(1,qualified.getSize());
        qualified.unQualifyTeam(team1);
        assertEquals(0,qualified.getSize());
    }

    @Test
    public void testUnQualifyTeamDoesNotExist() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        QualifiedTeams qualified = new QualifiedTeams();
        assertEquals(0,qualified.getSize());
        qualified.unQualifyTeam(team1);
        assertEquals(0,qualified.getSize());
    }

    @Test
    public void testTeamList() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        QualifiedTeams qualified = new QualifiedTeams();

        try {
            qualified.qualifyTeam(team1);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }

        assertEquals("Teams = [Zoomers]", qualified.teamList());

        HockeyTeam team2 = new HockeyTeam("Boomers", 2, 5);

        try {
            qualified.qualifyTeam(team2);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }

        assertEquals("Teams = [Zoomers, Boomers]", qualified.teamList());
    }

    @Test
    public void testTopTeam() {
        QualifiedTeams qualified = new QualifiedTeams();
        HockeyTeam team1 = new HockeyTeam("Zoomers", 3, 3);
        HockeyTeam team2 = new HockeyTeam("Boomers", 6, 0);
        HockeyTeam team3 = new HockeyTeam("Soomers", 5, 1);

        try {
            qualified.qualifyTeam(team1);
            qualified.qualifyTeam(team2);
            qualified.qualifyTeam(team3);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }

        assertEquals(team2, qualified.topTeam());
    }

    @Test
    public void testGetTeam() {
        QualifiedTeams qualified = new QualifiedTeams();
        HockeyTeam team1 = new HockeyTeam("Zoomers", 3, 3);
        HockeyTeam team2 = new HockeyTeam("Boomers", 6, 0);
        HockeyTeam team3 = new HockeyTeam("Soomers", 5, 1);

        try {
            qualified.qualifyTeam(team1);
            qualified.qualifyTeam(team2);
            qualified.qualifyTeam(team3);
        } catch (TeamAlreadyExistsException e) {
            fail();
        }

        assertEquals(team1, qualified.getTeam(0));
        assertEquals(team2, qualified.getTeam(1));
        assertEquals(team3, qualified.getTeam(2));
    }
}
