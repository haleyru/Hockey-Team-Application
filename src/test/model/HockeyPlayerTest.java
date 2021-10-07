package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HockeyPlayerTest {

    @Test
    public void testConstructor() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        assertEquals("Haley Russell", a.getName());
        assertEquals(10, a.getGoals());
        assertEquals(5, a.getAssists());
        assertEquals(15, a.getPoints());
    }

    @Test
    public void testAddGoal() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 0, 0);
        assertEquals(0, a.getGoals());
        assertEquals(0, a.getAssists());
        assertEquals(0, a.getPoints());

        a.addGoal();

        assertEquals(1, a.getGoals());
        assertEquals(0, a.getAssists());
        assertEquals(1, a.getPoints());
    }

    @Test
    public void testAddAssist() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 0, 0);
        assertEquals(0, a.getGoals());
        assertEquals(0, a.getAssists());
        assertEquals(0, a.getPoints());

        a.addAssist();

        assertEquals(0, a.getGoals());
        assertEquals(1, a.getAssists());
        assertEquals(1, a.getPoints());
    }

    @Test
    public void testToString() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        assertEquals("[Name = Haley Russell, Points = 15, Goals = 10, Assists = 5]", a.toString());
    }

    @Test
    public void hitoString() {
        HockeyPlayer a = new HockeyPlayer("Haley Russell", 10, 5);
        HockeyPlayer2 b = new HockeyPlayer2("Haley Russell", 10, 5);

        assertFalse(a == b);
    }

}