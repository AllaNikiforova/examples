package ru.ser;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class ServerClientThread extends Thread {
    private ClientSocket clientSocket;
    private Group group;
    private Player player;
    private int timeSecondsBeforeStart;
    private final int TIMESECONDSAFTERSTART = 180;

    public ServerClientThread(Socket clientSocket, Group group, Player player, int timeSecondsBeforeStart) throws IOException {
        this.clientSocket = new ClientSocket(clientSocket);
        this.timeSecondsBeforeStart = timeSecondsBeforeStart;
        this.player = player;
        this.group = group;
    }

    public void run() {
        try {
            System.out.println("ServerClientThread: соединение установлено");
            clientSocket.writeInt(timeSecondsBeforeStart);
            player.setPlayerName(clientSocket.readUTF());
            System.out.println("ServerClientThread: Передали время");


            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int tAfterStart = TIMESECONDSAFTERSTART;

                @Override
                public void run() {
                    try {
                        if (timeSecondsBeforeStart > 0) {
                            timeSecondsBeforeStart--;
                            String players = getStringPlayersBeforeStart();
                            try {
                                clientSocket.writeUTF(players);
                            } catch (IOException e) {
                                player.setErrorFlag(true);
                                timer.cancel();
                            }

                            if (timeSecondsBeforeStart == 5) {
                                try {
                                    clientSocket.writeUTF(group.getText());
                                    clientSocket.writeInt(TIMESECONDSAFTERSTART);
                                } catch (IOException e) {
                                    player.setErrorFlag(true);
                                    timer.cancel();
                                }
                            }
                        } else if (tAfterStart > 0 && !group.allFinished()) {
                            try {
                                player.setCharactersTyped(clientSocket.readInt());
                                player.setErrorsMade(clientSocket.readInt());
                                player.setProcess(Math.round(player.getCharactersTyped() * 100 / group.getLenOfText()));

                                clientSocket.writeUTF(getStringPlayersAfterStart(TIMESECONDSAFTERSTART - tAfterStart));
                                if (player.getProcess() == 100 && player.getFinishTime() == 0) {
                                    player.setFinishTime(TIMESECONDSAFTERSTART - tAfterStart);
                                    player.setTypingTime(TIMESECONDSAFTERSTART - tAfterStart);
                                }
                            } catch (IOException e) {
                                player.setErrorFlag(true);
                                timer.cancel();
                            }
                            tAfterStart--;
                        } else {
                            clientSocket.writeUTF(getFinalTable());
                            clientSocket.close();
                            System.out.println("Закрыли соединение с группой");
                            timer.cancel();
                        }
                    } catch (SocketException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getStringPlayersBeforeStart() {
        String res = "";
        int i = 1;
        group.checkPlayers();

        for (Player player : group.getPlayers()) {
            res += i++ + ". " + player.getPlayerName();
            if (this.player.equals(player)) {
                res += " (это Вы)";
            }
            res += "\n";
        }
        return res;
    }

    public String getStringPlayersAfterStart(int time) {
        group.checkPlayers();
        String res = "";
        int i = 1;

        List<Player> playerss = group.sortPlayers();

        for (Player player : playerss) {
            if (!player.isErrorFlag()) {
                res += i++ + ". " + player.getPlayerName() + " ";
                if (this.player.equals(player)) {
                    res += "(это Вы) ";
                }

                res += player.getProcess() + "%, " + player.getErrorsMade() + " ошибки(-ок), ";
                player.setTypingTime(time);
                if (player.getProcess() == 100) {
                    if (player.getFinishTime() <= 60) {
                        res += player.getCharactersTyped() + " сим/мин\n";
                    } else {
                        res += Math.round(player.getCharactersTyped() / player.getFinishTime() / 60.0f) + " сим/мин\n";
                    }
                    player.setTypingTime(player.getFinishTime());
                } else if (time < 60) {
                    res += player.getCharactersTyped() + " сим/мин\n";
                } else {
                    res += Math.round(player.getCharactersTyped() / (time / 60.0f)) + " сим/мин\n";
                }
            }
        }
        return res;
    }

    public String getFinalTable() {
        group.checkPlayers();
        List<Player> playerss = group.sortPlayers();

        String res = "\uD83C\uDFC6 Результаты \uD83C\uDFC6 \n";
        int i = 1;

        for (Player player : playerss) {
            if (!player.isErrorFlag()) {
                res += i++ + ". " + player.getPlayerName() + " ";
                if (this.player.equals(player)) {
                    res += "(это Вы) ";
                }

                if (player.getProcess() == 100) {
                    res += player.getTypingTime() + " сек, " + player.getErrorsMade() + " ошибки(-ок), ";
                } else {
                    System.out.println("TIMESECONDSAFTERSTART");
                    res += TIMESECONDSAFTERSTART + " сек, " + player.getErrorsMade() + " ошибки(-ок), ";
                }
                res += player.getCharactersTyped() + " символов\n";
            }
        }
        return res;
    }
}
