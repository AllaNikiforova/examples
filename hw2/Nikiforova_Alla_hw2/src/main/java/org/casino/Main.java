package org.casino;

import java.util.*;

public class Main {
    /**
     * Переменная для обозначения количества игрового времени
     */
    public static int PLAYTIME = 35000;
    public static int t;
    public static Date startTime;

    /**
     * Для обработки входящей строки в консоль
     * @param line то, что пользователь вбивает после вопроса "Запосутить игру еще раз?"
     * @return нужно ли завершить программу или нет
     */
    public static Boolean checkLine(String line) {
        if (line.equals("exit")) {
            System.out.println("До свидания\uD83D\uDE1E");
        } else if (line.equals("играть заново")) {
            System.out.println("Окей, играем еще раз\uD83D\uDE0E \n\n\n\n\n");
            return true;
        } else {
            System.out.println("Мы не поняли, что ты ввел. Программа завершает свою работу\uD83D\uDE2D");
        }
        return false;
    }

    /**
     * Останавливаем (прерываем доступ игрокам к игровому столу) игроков, чтобы они не смогли добавить баллы к
     * текущей или финальной таблице результатов
     *
     * @param teams Команды, которым надо заблокировать доступ к игровому столу
     */
    public static void stopAllThreads(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).setStop();
        }
    }

    /**
     * Восстанавливаем игрокам доступ к игровому столу
     *
     * @param teams Команды, которым надо восстановить доступ к игровому столу
     */
    public static void continueAllThreads(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).continueWork();
        }
    }

    /**
     * Объявление финальных результатов игры
     *
     * @param teams   Команды-игроки в игру
     * @param winners Победители-игроки в игру
     */
    public static void results(List<Team> teams, List<Team> winners) {
        if (winners.size() == 1)
            System.out.println("\n\uD83E\uDD47Победитель игры:\uD83E\uDD47");
        else
            System.out.println("\n\uD83E\uDD47Победители игры:\uD83E\uDD47");

        for (Team winner : winners) {
            System.out.println(winner.getTeamName() + ": " + winner.getScore());
        }

        int prize = prize();
        if (winners.size() == 1)
            System.out.println("\n\uD83D\uDCB4Команде достается:\uD83D\uDCB4");
        else
            System.out.println("\n\uD83D\uDCB4Командам достается по:\uD83D\uDCB4");

        for (int i = 0; i < winners.size(); i++) {
            System.out.println(winners.get(i).getTeamName() + ": " + prize / winners.size() + "¥");
            winners.get(i).setPrize(prize / winners.size());
        }

        LinkedList<Team> teamList = new LinkedList();

        for (int i = 0; i < teams.size(); i++) {
            teamList.add(teams.get(i));
        }
        teamList.sort((t1, t2) -> t2.getScore() - t1.getScore());

        //🏆эмодзи
        System.out.println("\n===============================================================");
        System.out.println("=                    \uD83C\uDFC6Общие результаты:\uD83C\uDFC6                    =");
        System.out.println("===============================================================");

        for (Team team : teamList) {
            System.out.println(team.getTeamName() + " - " + team.getScore());
        }

        System.out.println("===============================================================\n\n");


        //🤑 эмодзи
        System.out.println("Каждый игрок заработал по:\uD83E\uDD11\uD83E\uDD11\uD83E\uDD11\uD83E\uDD11\uD83E\uDD11\uD83E\uDD11\n");

        for (Team winner : winners) {
            List<Player> players = winner.getPlayers();
            for (int j = 0; j < 3; j++) {
                if (winner.getScore() == 0) {
                    players.get(j).setPrize(winner.getPrize() / 3);
                } else {
                    players.get(j).setPrize(players.get(j).getScoreFinal() * winner.getPrize() / winner.getScore());
                }

                System.out.println(players.get(j).getPlayerName() + " игрок: из команды " + winner.getTeamName() + " заработал - " +
                        String.format("%.2f", players.get(j).getPrize()) + "¥!");
            }
            System.out.println("\n");
        }
    }

    /**
     * Определение текущих лидеров игры
     *
     * @param teams Команды-игроки
     * @return
     */
    public static List<Team> winners(List<Team> teams) {
        List<Team> currentWinners = new ArrayList<>();
        int currentMaxScore = -1;

        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getScore() > currentMaxScore) {
                currentWinners.clear();
                currentWinners.add(teams.get(i));
                currentMaxScore = teams.get(i).getScore();
            } else if (teams.get(i).getScore() == currentMaxScore) {
                currentWinners.add(teams.get(i));
            }
        }

        return currentWinners;
    }

    /**
     * Генерация рандомного приза
     *
     * @return Целое число денежного приза
     */
    public static int prize() {
        return 1_000_000 + (int) (Math.random() * 9_000_001);
    }

    /**
     * Ждет завершения работы всех команд и игроков
     * @param teams команды, которые н
     * @throws InterruptedException если будет ошибка при прерывании потоков
     */
    public static void endWork(List<Team> teams) throws InterruptedException {
        for (Team team : teams) {
            team.interrupt();
            List<Player> players = team.getPlayers();
            for (Player player : players) {
                player.join();
            }
            team.join();
        }
    }

    /**
     * Метод для корректной работы таймера
     * @param teams список команд, играющих в казино
     */
    public static void runForTimer(List<Team> teams) {
        stopAllThreads(teams);

        List<Team> currentWinners = winners(teams);
        System.out.println("Текущий лидер игры: ");
        for (Team currentWinner : currentWinners) {
            System.out.println(currentWinner.getTeamName() + ": " + currentWinner.getScore());
        }

        System.out.println("⏳До конца игры: " + ((PLAYTIME - (new Date().getTime() - startTime.getTime())) / 1000 + 1) + " секунд⏳\n");
        continueAllThreads(teams);
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.out.println("Введите количество команд в качестве параметра t\n" +
                    "Пример запуска jar:\n" +
                    "java -jar application.jar 10, где 10 = t - количество команд");
        } else {
            t = Integer.parseInt(args[0]);

            if (t <= 0 || t > 10) {
                System.out.println("Количество команд не может быть равным: " + t + ". Программа завершает свою работу\uD83D\uDE2D");
            } else {
                Scanner scanner = new Scanner(System.in);
                int counter = 0;
                while ((counter == 0) || (scanner.hasNext())) {
                    if (counter > 0) {
                        if (!checkLine(scanner.nextLine())) {
                            break;
                        }
                    }
                    counter++;
                    List<Team> teams = new ArrayList<>();
                    RandomTeamName randomTeamName = new RandomTeamName();
                    RandomName randomName = new RandomName();

                    for (int i = 0; i < t; i++) {
                        teams.add(new Team(randomTeamName.getFreeTeamName(), randomName));
                    }

                    startTime = new Date();
                    for (int i = 0; i < t; i++) {
                        teams.get(i).start();
                    }

                    Timer timer = new Timer("Timer");
                    TimerTask task = new TimerTask() {
                        public void run() {
                            runForTimer(teams);
                        }
                    };

                    timer.scheduleAtFixedRate(task, 100, 10_000);

                    Date newTime = new Date();
                    while ((newTime.getTime() - startTime.getTime()) < PLAYTIME) {
                        newTime = new Date();
                    }

                    timer.cancel();
                    stopAllThreads(teams);
                    List<Team> winners = winners(teams);
                    results(teams, winners);
                    endWork(teams);

                    System.out.println("\n\n\n\n\nВведите 'exit', чтобы выйти, или 'играть заново', чтобы запустить новую игру");
                }
            }
        }
    }
}