package org.example;

import java.util.AbstractMap;
import java.util.Map;

import static org.example.LocalCache.locationIdToZone;

public class TaxiRideAgnosticMapper {
    public static Map.Entry<String, Integer> Process(TaxiRide ride){
        if(ride.paymentType !=2 ){
            return null;
        }

        String zoneName = locationIdToZone.get(ride.puLocationId);
        if (zoneName == null) {
            return null;
        }

        var key = ride.pickupDatetime.getYear() + "-" + ride.pickupDatetime.getMonthValue()  + "," + ride.puLocationId;

        return new AbstractMap.SimpleEntry<>(key, ride.passengerCount);
    }
}
