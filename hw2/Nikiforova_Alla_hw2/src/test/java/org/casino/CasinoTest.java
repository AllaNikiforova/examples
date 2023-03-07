package org.casino;

import static org.casino.Main.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CasinoTest {
    @Test
    public void correctPrize() {
        for (int i = 0; i < 100_000_000; i++) {
            assertTrue((prize() >= 1_000_000) && (prize() <= 10_000_000));
        }
    }

    //тест на совпадение выигрыша команды и участников
    private List<Team> teams;
    private RandomName randomName;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void before() {
        teams = new ArrayList<>();
        randomName = new RandomName();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testStopAllThreads() {
        teams.add(new Team("Team 1", randomName));
        teams.add(new Team("Team 2", randomName));
        Main.stopAllThreads(teams);
        for (Team team : teams) {
            assertTrue(team.isStopped());
        }
    }

    @Test
    public void testContinueAllThreads() {
        teams.add(new Team("Team 1", randomName));
        teams.add(new Team("Team 2", randomName));
        Main.continueAllThreads(teams);
        for (Team team : teams) {
            assertFalse(team.isStopped());
        }
    }

    @Test
    public void testWinners() {
        teams.add(new Team("Team 1", randomName));
        teams.add(new Team("Team 2", randomName));
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);

        team1.setScore(10);
        team2.setScore(20);

        List<Team> currentWinners = winners(teams);
        assertEquals(1, currentWinners.size());
        assertTrue(currentWinners.contains(team2));

        team1.setScore(20);
        team2.setScore(20);

        currentWinners = winners(teams);
        assertEquals(2, currentWinners.size());
        assertTrue(currentWinners.contains(team1));
        assertTrue(currentWinners.contains(team2));
    }

    @Test
    public void testResults() {
        teams.add(new Team("Team 1", randomName));
        teams.get(0).setScore(100);
        teams.add(new Team("Team 2", randomName));
        teams.get(1).setScore(50);
        teams.add(new Team("Team 3", randomName));
        teams.get(2).setScore(75);

        List<Team> winners = winners(teams);
        assertEquals(1, winners.size());
        assertEquals("Team 1", winners.get(0).getTeamName());

        results(teams, winners);

        //assertEquals("\n\uD83E\uDD47Победитель игры:\uD83E\uDD47", outputStreamCaptor.toString().trim());

    }

    @Test
    public void testCorrectScore() {
        teams.add(new Team("Team 1", randomName));
        teams.get(0).setScore(100);

        List<Player> players = teams.get(0).getPlayers();
        players.get(0).setScoreFinal(40);
        players.get(1).setScoreFinal(20);
        players.get(2).setScoreFinal(40);

        int sumPlayersScore = players.get(0).getScoreFinal() + players.get(1).getScoreFinal() +players.get(2).getScoreFinal();
        assertEquals(sumPlayersScore, teams.get(0).getScore());

        teams.add(new Team("Team 2", randomName));
        teams.get(1).setScore(50);
        teams.add(new Team("Team 3", randomName));
        teams.get(2).setScore(75);

        List<Team> winners = winners(teams);
    }

    @Test
    public void testCorrectPrize() {
        teams.add(new Team("Team 1", randomName));
        teams.get(0).setScore(100);

        List<Player> players = teams.get(0).getPlayers();
        players.get(0).setScoreFinal(50);
        players.get(1).setScoreFinal(20);
        players.get(2).setScoreFinal(30);

        teams.add(new Team("Team 2", randomName));
        teams.get(1).setScore(50);
        teams.add(new Team("Team 3", randomName));
        teams.get(2).setScore(75);

        List<Team> winners = winners(teams);

        int prize = prize();
        for (int i = 0; i < winners.size(); i++) {
            winners.get(i).setPrize(prize / winners.size());
        }

        Team winner = winners.get(0);
        List<Player> playersWinner = winner.getPlayers();
        for (int j = 0; j < 3; j++) {
            playersWinner.get(j).setPrize(playersWinner.get(j).getScoreFinal() * winner.getPrize() / winner.getScore());
        }

        float sumPlayersPrizes = playersWinner.get(0).getPrize() + playersWinner.get(1).getPrize() + playersWinner.get(2).getPrize();

        assertTrue(Math.abs(sumPlayersPrizes - winner.getPrize()) < 1);
    }

    @Test
    public void testEndWorkWithEmptyTeamList() throws InterruptedException {
        List<Team> emptyTeams = new ArrayList<>();
        endWork(emptyTeams);
        assertTrue(emptyTeams.isEmpty());
    }

    @Test
    public void testEndWork() throws InterruptedException {
        Team team1 = new Team("Team 1", randomName);
        Team team2 = new Team("Team 2", randomName);
        teams.add(team1);
        teams.add(team2);

        endWork(teams);

        assertTrue(teams.get(0).isInterrupted());
        assertTrue(teams.get(1).isInterrupted());
    }

    @Test
    public void testCheckLine() {
        Boolean answer = checkLine("exit");
        assertEquals("До свидания\uD83D\uDE1E", outputStreamCaptor.toString().trim());
        assertFalse(answer);

        assertTrue(checkLine("играть заново"));
        assertFalse(checkLine("ksjfdnvkjsv"));
    }


    @Test
    public void testRunForTimer() throws InterruptedException {
        startTime = new Date();
        runForTimer(teams);
        Thread.sleep(PLAYTIME);
        for (Team team : teams) {
            assertFalse(team.isAlive());
            List<Player> players = team.getPlayers();
            for (Player player : players) {
                assertFalse(player.isAlive());
            }
        }
    }

    @Test
    public void testRunForTimerWithNoTeams() {
        List<Team> emptyTeams = new ArrayList<>();
        assertDoesNotThrow(() -> runForTimer(emptyTeams));
    }
}
