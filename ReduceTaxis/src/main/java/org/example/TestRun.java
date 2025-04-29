package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TestRun {

    public void Run() throws IOException {
        Path path = Paths.get("C:/Users/Oskar/Documents/Projects/PutBigDataMapReduceTaxis/input/datasource1/part-00001");

        Map<String, List<AppDataAgnosticMapper.AppDataAggregate>> mappedResults = new HashMap<>();

        Files.lines(path).forEach(line -> {
            var appData = AppDataParser.parseLine(line);

            var mapping = AppDataAgnosticMapper.process(appData);
            if (mapping != null) {
                String key = mapping.getKey();
                AppDataAgnosticMapper.AppDataAggregate value = mapping.getValue();

                mappedResults.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
        });

        Map<String, String> reducedResults = new HashMap<>();

        mappedResults.forEach((key, values) -> {
            int totalRatings = 0;
            int totalRatingCount = 0;
            int appCount = 0;

            for (AppDataAgnosticMapper.AppDataAggregate value : values) {
                totalRatings += value.totalRatings;
                totalRatingCount += value.totalRatingCount;
                appCount += value.appCount;
            }

            double averageRating = totalRatingCount == 0 ? 0 : (double) totalRatings / totalRatingCount;

            String result = String.format("totalRatings: %d, totalRatingCount: %d, appCount: %d, averageRating: %.2f",
                    totalRatings, totalRatingCount, appCount, averageRating);

            reducedResults.put(key, result);
        });

        reducedResults.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Result: " + value);
        });
    }
}
