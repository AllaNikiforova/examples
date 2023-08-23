package ru.ser;

import java.util.*;

public class Group {
    private Set<Player> players;
    private String text;
    private int lenOfText;
    private final int numberGroup;

    private int timeSecondsBeforeStart = 30;

    public Group(int i) {
        players = new HashSet<>();
        numberGroup = i;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timeSecondsBeforeStart > 0) {
                    timeSecondsBeforeStart--;
                } else {
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public boolean addPlayer(Player player) {
        System.out.println("timeSecondsBeforeStart: " + timeSecondsBeforeStart);
        System.out.println("players.size(): " + players.size());
        System.out.println("players: " + players);
        if (timeSecondsBeforeStart <= 5 || players.size() >= 3 || players.contains(player)) {
            return false;
        }
        players.add(player);
        return true;
    }

    public String getText() {
        return text;
    }

    public int getLenOfText() {
        return lenOfText;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setText(String text) {
        this.text = text;
        this.lenOfText = text.length() - (text.split(" ").length - 1);
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public int getTimeSecondsBeforeStart() {
        return timeSecondsBeforeStart;
    }

    public void checkPlayers() {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player key = iterator.next();
            if (key.isErrorFlag()) {
                iterator.remove();
            }
        }
    }

    public boolean allFinished() {
        this.checkPlayers();
        for (Player player: players) {
            if (player.getProcess() < 100) {
                return false;
            }
        }
        return true;
    }

    public List<Player> sortPlayers() {
        List<Player> playerss = new ArrayList<>();
        for (Player player: players) {
            playerss.add(player);
        }

        System.out.println("размер playerss до сортировки: " + playerss.size());

        Collections.sort(playerss);
        System.out.println("размер playerss после сортировки: " + playerss.size());

        return playerss;
    }
}
