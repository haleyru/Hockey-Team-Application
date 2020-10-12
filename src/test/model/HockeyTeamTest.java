package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HockeyTeamTest {

    @Test
    public void testConstructor() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        assertEquals("Zoomers", team1.getTeamName());
        assertEquals(0, team1.getWins());
        assertEquals(0, team1.getLosses());
        assertEquals(0, team1.getGamesPlayed());
        assertEquals(0, team1.getTeamSize());
    }

    @Test
    public void testAddWin() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        team1.addWin();
        assertEquals(1, team1.getWins());
        assertEquals(1, team1.getGamesPlayed());
    }

    @Test
    public void testAddLoss() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        team1.addLoss();
        assertEquals(1, team1.getLosses());
        assertEquals(1, team1.getGamesPlayed());
    }

    @Test
    public void testAddPlayer() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        assertEquals(0, team1.getTeamSize());
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        team1.addPlayer(a);
        assertEquals(1, team1.getTeamSize());
    }

    @Test
    public void testAddAndRemovePlayer() {
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        assertEquals(0, team1.getTeamSize());
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        HockeyPlayer b = new HockeyPlayer("AJ Reiter", 25, 10);
        team1.addPlayer(a);
        team1.addPlayer(b);
        assertEquals(2, team1.getTeamSize());

        team1.removePlayer(a);
        assertEquals(1, team1.getTeamSize());
    }

    @Test
    public void testPlayerList() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        HockeyTeam team1 = new HockeyTeam("Zoomers", 0, 0);
        team1.addPlayer(a);

        assertEquals("Players = [Haley Russell]", team1.playerList());

        HockeyPlayer b = new HockeyPlayer("AJ Reiter", 25, 10);
        team1.addPlayer(b);

        assertEquals("Players = [Haley Russell, AJ Reiter]", team1.playerList());
    }
}
