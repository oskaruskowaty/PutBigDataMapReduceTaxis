package org.example;

import java.util.AbstractMap;
import java.util.Map;

public class AppDataAgnosticMapper {

    public static Map.Entry<String, AppDataAggregate> process(AppData appData) {
        // Pomijamy aplikacje z mniej niż 1000 ocen
        if (appData.ratingCount < 1000 || appData.released == null) {
            return null;
        }

        int releaseYear = appData.released.getYear();

        String key = appData.developerId + "," + releaseYear;

        AppDataAggregate aggregate = new AppDataAggregate();

        aggregate.totalRatings += appData.rating == null ? 0 : appData.rating * appData.ratingCount;
        aggregate.totalRatingCount += appData.ratingCount;
        aggregate.appCount++;

        return new AbstractMap.SimpleEntry<>(key, aggregate);
    }

    // Klasa agregująca dane
    public static class AppDataAggregate {
        public double totalRatings = 0;
        public int totalRatingCount = 0;
        public int appCount = 0;
    }
}
