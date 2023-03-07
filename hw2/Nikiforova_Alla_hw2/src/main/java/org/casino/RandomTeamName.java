package org.casino;

import java.util.ArrayList;
import java.util.List;

/**
 *  Класс с рандомными именами для команд. СВободные имена указываются в freeTeamNamesA
 */
public class RandomTeamName {
    private final String[] freeTeamNames = new String[]{
            "Mongoose", "Narwhal", "Boa", "Wolf", "Mollusc",
            "Suslik", "Shoulder", "Ruble", "Dog", "Marten"
    };

    private final ArrayList<String> freeTeamNamesA = new ArrayList<>(List.of(freeTeamNames));

    /**
     * Получить свободное рандомное имя. При этом среди свободных имен это имя уже не числится
     * @return свободное рандомное имя для команды
     */
    public String getFreeTeamName() {
        return freeTeamNamesA.remove((int) (Math.random() * (freeTeamNamesA.size() - 1)));
    }

    /**
     * Возвращает количество незанятых имен для команд
     * @return Количество свободных имен
     */
    public int getCountOfFreeTeamNames () {
        return freeTeamNamesA.size();
    }
}
