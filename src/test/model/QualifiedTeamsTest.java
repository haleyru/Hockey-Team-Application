package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QualifiedTeamsTest {

    @Test
    public void testQualifyTeam() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        QualifiedTeams qualified = new QualifiedTeams();
        qualified.qualifyTeam(team1);
        assertEquals(1,qualified.getSize());
    }

    @Test
    public void testUnQualifyTeam() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        QualifiedTeams qualified = new QualifiedTeams();
        qualified.qualifyTeam(team1);
        assertEquals(1,qualified.getSize());

        qualified.unQualifyTeam(team1);
        assertEquals(0,qualified.getSize());
    }

    @Test
    public void testTeamList() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        QualifiedTeams qualified = new QualifiedTeams();
        qualified.qualifyTeam(team1);
        assertEquals("Teams = [Zoomers]", qualified.teamList());

        HockeyTeam team2 = new HockeyTeam("Boomers", 2, 5);
        qualified.qualifyTeam(team2);

        assertEquals("Teams = [Zoomers, Boomers]", qualified.teamList());
    }

    @Test
    public void testTopTeam() {
        QualifiedTeams qualified = new QualifiedTeams();
        HockeyTeam team1 = new HockeyTeam("Zoomers", 3, 3);
        HockeyTeam team2 = new HockeyTeam("Boomers", 6, 0);
        HockeyTeam team3 = new HockeyTeam("Soomers", 5, 1);

        qualified.qualifyTeam(team1);
        qualified.qualifyTeam(team2);
        qualified.qualifyTeam(team3);

        assertEquals(team2, qualified.topTeam());
    }

    @Test
    public void testGetTeam() {
        QualifiedTeams qualified = new QualifiedTeams();
        HockeyTeam team1 = new HockeyTeam("Zoomers", 3, 3);
        HockeyTeam team2 = new HockeyTeam("Boomers", 6, 0);
        HockeyTeam team3 = new HockeyTeam("Soomers", 5, 1);

        qualified.qualifyTeam(team1);
        qualified.qualifyTeam(team2);
        qualified.qualifyTeam(team3);

        assertEquals(team1, qualified.getTeam(0));
        assertEquals(team2, qualified.getTeam(1));
        assertEquals(team3, qualified.getTeam(2));
    }
}
