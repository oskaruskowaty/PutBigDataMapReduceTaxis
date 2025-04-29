package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AppDataParser {

    private static final DateTimeFormatter[] patterns = {
            DateTimeFormatter.ofPattern("MMM d, yyyy"),   // e.g., Sep 7, 2019
            DateTimeFormatter.ofPattern("MMM dd, yyyy"),  // e.g., Sep 07, 2019
            DateTimeFormatter.ofPattern("yyyy-MM-dd")     // e.g., 2019-09-07
    };
    public static AppData parseLine(String line) {
        String[] fields = line.split("\u0001", -1); // -1 zachowuje puste ciągi

        AppData appData = new AppData();

        appData.appName = fields[0];
        appData.appId = fields[1];
        appData.category = fields[2];
        if (IsNumericValid(fields[3])) {
            appData.rating = Double.parseDouble(fields[3]);
        }
        if (IsNumericValid(fields[4])) {
            appData.ratingCount = Integer.parseInt(fields[4]);
        }
        appData.installs = fields[5];
        appData.minimumInstalls = fields[6];
        appData.maximumInstalls = fields[7];
        appData.isFree = fields[8].equals("1");
        appData.price = fields[9].isEmpty() ? 0.0 : Double.parseDouble(fields[9]);
        appData.currency = fields[10];
        appData.size = fields[11];
        appData.minimumAndroid = fields[12];
        appData.released = parsesSimpleDate(fields[13]);
        appData.lastUpdated = parsesSimpleDate(fields[14]);
        appData.contentRating = fields[15];
        appData.privacyPolicy = fields[16];
        appData.isAdSupported = fields[17].equals("1");
        appData.hasInAppPurchases = fields[18].equals("1");
        appData.isEditorsChoice = fields[19].equals("1");
        appData.developerId = fields[21];

        return appData;
    }

    private static boolean IsNumericValid(String val){
        if (val== null || val.isEmpty() || val.equals("null")) {
            return false;
        }
        return true;
    }

    private static LocalDate parsesSimpleDate(String dateStr) {
        if (dateStr.isEmpty() || dateStr.equals("~")) {
            return null;
        }
        for (DateTimeFormatter pattern : patterns) {
            try {
                // Try to parse the date with the current pattern
                return LocalDate.parse(dateStr, pattern);
            } catch (DateTimeParseException e) {
                // Ignore the exception and try the next pattern
            }
        }
        return null;
    }
}
