package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaxiRideParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static TaxiRide parseLine(String csvLine) {
        String[] fields = csvLine.split(",", -1); // -1 to retain empty strings

        TaxiRide ride = new TaxiRide();

        ride.vendorId = Integer.parseInt(fields[0]);
        ride.pickupDatetime = LocalDateTime.parse(fields[1], formatter);
        ride.dropoffDatetime = LocalDateTime.parse(fields[2], formatter);
        ride.passengerCount = Integer.parseInt(fields[3]);
        ride.tripDistance = Double.parseDouble(fields[4]);
        ride.rateCodeId = Integer.parseInt(fields[5]);
        ride.storeAndFwdFlag = fields[6];
        ride.puLocationId = Integer.parseInt(fields[7]);
        ride.doLocationId = Integer.parseInt(fields[8]);
        ride.paymentType = Integer.parseInt(fields[9]);
        ride.fareAmount = Double.parseDouble(fields[10]);
        ride.extra = Double.parseDouble(fields[11]);
        ride.mtaTax = Double.parseDouble(fields[12]);
        ride.tipAmount = Double.parseDouble(fields[13]);
        ride.tollsAmount = Double.parseDouble(fields[14]);
        ride.improvementSurcharge = Double.parseDouble(fields[15]);
        ride.totalAmount = Double.parseDouble(fields[16]);

        return ride;
    }
}
