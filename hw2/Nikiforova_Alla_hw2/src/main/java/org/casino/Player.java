package org.casino;

/**
 * Поток, представляющий игрока
 */
public class Player extends Thread {
    private final String name;
    private final String teamName;
    private final Team team;
    private int scoreFinal;
    private int scoreTemporary;
    private float prize;

    /**
     * Конструктор потока "Игрок"
     * @param name Уникальное имя игрока
     * @param teamName Имя команды, в состав которой входит игрок
     * @param team Команда, в которую взодит игрок
     */
    Player(String name, String teamName, Team team) {
        this.name = name;
        this.teamName = teamName;
        this.team = team;

        scoreFinal = 0;
        scoreTemporary = 0;
        prize = 0;
    }

    /**
     * Возвращает финальное количество очков, заработанных игроком
     * @return финальное количество очков
     */
    public int getScoreFinal() {
        return scoreFinal;
    }

    /**
     * Ставит финальное количество очков, заработанных игроком
     * @param scoreFinal финальное количество очков
     */
    public void setScoreFinal(int scoreFinal) {
    this.scoreFinal = scoreFinal;
    }

    /**
     * Возвращает имя игрока
     * @return Строка - имя игрока
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * Здоровается. Игрок пытается получить доступ к столу (или этот доступ не заблокирован), пока сам не прерван, подбрасывает кость,
     * записывает результат. Прощается со всеми.
     */
    @Override
    public void run() {
        System.out.printf("Всем привет, Я %s из %s%n", name, teamName);

        while (!Thread.currentThread().isInterrupted()) {
            while (!team.free) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }

            if (!Thread.currentThread().isInterrupted()) {
                scoreTemporary = team.throwADice();
                scoreFinal += scoreTemporary;

                try {
                    Thread.sleep(getRandomTimeToSleep());
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        System.out.println("Пока, казино! Я " + name + " thread from " + teamName + " пошел домой!");
    }

    /**
     * Определяет рандомное количество миллисекунд (для сна потока)
     * @return целое количество секунд
     */
    public static int getRandomTimeToSleep() {
        return 100 + (int) (Math.random() * 901);
    }

    /**
     * Проставляет приз игроку
     * @param prize число с запятой - индивидуальный приз игрока
     */
    public void setPrize(float prize) {
        this.prize = prize;
    }

    /**
     * Вовзращает заработанный (или нет - тогда ноль) приз игрока
     * @return Приз игрока
     */
    public float getPrize() {
        return prize;
    }
}
