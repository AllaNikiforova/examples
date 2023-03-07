package org.casino;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс (поток) для команды игроков в кости
 */
public class Team extends Thread {
    private final String name;
    private int score;
    public Boolean free;
    private Boolean stop;
    private final List<Player> players;
    private float prize;

    /**
     * Конструкток потока "Команда"
     * @param name Уникалольное имя для класса
     * @param randomName Экземляр класса, чтобы называть игроков класса уникальными именами
     */
    Team(String name, RandomName randomName) {
        this.name = name;
        players = new ArrayList<>();
        score = 0;
        free = false;
        stop = false;
        prize = 0;

        for (int i = 0; i < 3; i++) {
            players.add(new Player(randomName.getFreeName(), name, this));
        }
    }

    /**
     * Геттер для возврата имени класса
     * @return имя команды
     */
    public String getTeamName() {
        return name;
    }

    /**
     * Геттер для возврата списка игроков команды
     * @return игроки команды
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Геттер для возврата количества очков команды
     * @return количество очков команды
     */
    public int getScore() {
        return score;
    }

    /**
     * Тут мы запускаем игроков, блокируем доступ к игровому столу (во время объявления результатов)
     * и останавливаем (прервываем) игроков.
     */
    @Override
    public void run() {
        players.get(0).start();
        players.get(1).start();
        players.get(2).start();

        free = true;

        while (!Thread.currentThread().isInterrupted()) {
            free = !stop;
        }

        for (int i = 0; i < 3; i++) {
            players.get(i).interrupt();
        }
    }

    /**
     * Синхронизированный метод для подбрасывания игровой кости 6 раз подряд. То есть в один момент этим
     * методом может воспользоваться только один игрок
     * Этот метод играет роль игрового стола
     * @return Количество очков, заработанных одним игроком за один подход к столу
     */
    synchronized int throwADice() {
        free = false;
        int scoreTemporary = 0;

        for (int i = 0; i < 6; i++) {
            scoreTemporary += Dice.throwADice();
        }

        this.score += scoreTemporary;
        free = true;
        return scoreTemporary;
    }

    /**
     * Блокирует доступ к игровому столу. Удобно использовать во время объявления результатов - чтобы
     * никто из игроков не добавил баллов к итоговому счету команды
     */
    public void setStop() {
        stop = true;
    }

    /**
     * Разблокирует доступ к игровому столу
     */
    public void continueWork() {
        stop = false;
    }

    /**
     * Выдает денежный приз для команды-победителя
     * @param prize Денежный выигрыш
     */
    public void setPrize(int prize) {
        this.prize = prize;
    }

    /**
     * Геттер для получения приза
     * @return сумма денежного приза
     */
    public float getPrize() {
        return prize;
    }

    /**
     * Для проверки - остановлен ли доступ к игровому столу игрокам или нет
     * @return true - если доступ к столу есть, false - если доступа к столу нет
     */
    public Boolean isStopped() {
        return stop;
    }

    /**
     * Проставляет счет команде
     * @param score нужный игровой счет команды
     */
    public void setScore(int score) {
        this.score = score;
    }
}
