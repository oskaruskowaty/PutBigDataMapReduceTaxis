package org.example;

import java.util.HashMap;
import java.util.Map;

public class MockMapReduce {
    public Map<String, Integer> aggregate = new HashMap<>();

    public void proccess(TaxiRide ride) {
        var keyValue = TaxiRideAgnosticMapper.Process(ride);

        if(keyValue == null) {
            return;
        }
        aggregate.put(keyValue.getKey(), aggregate.getOrDefault(keyValue.getKey(), 0) + keyValue.getValue());
    }
}
