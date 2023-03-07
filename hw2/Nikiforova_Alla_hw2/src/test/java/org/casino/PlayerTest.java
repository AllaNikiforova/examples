package org.casino;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    public void testSetAndGetScoreFinal() {
        Team team = new Team("TestTeamName", new RandomName());
        Player player = new Player("Alice", "Red Carrot", team);
        player.setScoreFinal(10);
        assertEquals(10, player.getScoreFinal());
    }

    @Test
    public void testGetPlayerName() {
        Team team = new Team("TestTeamName", new RandomName());
        Player player = new Player("Bob", "Blue apple", team);
        assertEquals("Bob", player.getPlayerName());
    }

    @Test
    public void testSetAndGetPrize() {
        Team team = new Team("TestTeamName", new RandomName());
        Player player = new Player("Charlie", "Green grass", team);
        player.setPrize(100.33f);
        assertEquals(100.33f, player.getPrize());
    }

    @Test
    public void testGetRandomTimeToSleep() {
        Team team = new Team("TestTeamName", new RandomName());
        Player player = new Player("Charlie", "Green grass", team);
        int timeToSleep = player.getRandomTimeToSleep();
        assertTrue(timeToSleep >= 100 && timeToSleep <= 1000);
    }
}