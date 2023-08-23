package org.casino;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс с рандомными именами для игроков. СВободные имена указываются в freeNamesA
 */
public class RandomName {
    private final String[] freeNames = new String[]{"Peter Walker", "Timothy Thornton", "Thomas Collins", "Robert Smith",
            "Larry Mack", "Joseph Richards", "Todd Harris", "Andrew Boyd", "Billy Johnson", "Steven Moore",
            "Darren Lane", "Bill Mason", "Francis Lee", "John Smith", "Michael Parker", "James Castillo",
            "Francisco Daniels", "Paul May", "Brian Larson", "William Miller", "Joseph Davis", "Raymond Brown",
            "Michael Evans", "George Jones", "Jose Ortega", "Thomas Graham", "Jeffrey White", "Ryan Anderson",
            "Warren Tucker", "Jimmy Beck", "Joseph Martin", "Alex Wilson", "Jeffrey Johnson", "Michael Cox",
            "Kenneth Smith", "Allen Lopez", "Francis Baker", "Alan May", "Sean Myers", "Stephen Smith",
            "Michael Arnold", "William Pierce", "Rick Pratt", "Victor Adams", "Clarence Casey", "Melvin Horton",
            "Joseph Lewis", "Jonathan Dennis", "David Schmidt", "Ronald Williams", "Brian Kelly", "Joseph Bailey",
            "Walter Jordan", "Luis Richardson", "James Craig", "Stanley Lewis", "Francis Foster", "Gregory Bowen",
            "Zachary Manning", "David Smith", "Randy Abbott", "Justin Fowler", "David Hicks", "Richard Marshall",
            "Richard Hall", "Brian Mitchell", "Gary Allen", "Gregory Long", "Kevin Williams", "Paul Harris", "Anthony Lewis",
            "Daniel Hudson", "Lester Sims", "Matthew Sutton", "Albert Tran", "Christopher Hernandez", "Pedro Perry",
            "Brian Ford", "Stephen Mitchell", "Leon Wade", "Roy Kelly", "Tim Wilson", "Shawn Fisher", "John Mullion",
            "David Robertson", "Ryan Robertson", "Donald Williams", "David Tucker", "Carl Patterson", "Charles Scott",
            "Gary Day", "Mike Robinson", "Christian Briggs", "Michael Jones", "Patrick Smith", "Lester Wilson",
            "Gary Chandler", "David Lynch", "Walter Smith", "Daniel Olson"
    };

    private final ArrayList<String> freeNamesA = new ArrayList<>(List.of(freeNames));

    /**
     * Получить свободное рандомное имя. При этом среди свободных имен это имя уже не числится
     * @return свободное рандомное имя для игрока
     */
    public String getFreeName() {
        return freeNamesA.remove((int) (Math.random() * (freeNamesA.size() - 1)));
    }

    /**
     * Возвращает количество незанятых имен для игроков
     * @return Количество свободных имен
     */
    public int getCountOfFreeNames() {
        return freeNamesA.size();
    }
}
