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
    public void testTopTeam() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 5, 2);
        HockeyTeam team2 = new HockeyTeam("Boomers", 2, 5);
        QualifiedTeams qualified = new QualifiedTeams();
        qualified.qualifyTeam(team1);
        qualified.qualifyTeam(team2);
        assertEquals(team1, qualified.topTeam());
    }
}
