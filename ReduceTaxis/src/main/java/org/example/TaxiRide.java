package org.example;

import java.time.LocalDateTime;

public class TaxiRide {
    public int vendorId;
    public LocalDateTime pickupDatetime;
    public LocalDateTime dropoffDatetime;
    public int passengerCount;
    public double tripDistance;
    public int rateCodeId;
    public String storeAndFwdFlag;
    public int puLocationId;
    public int doLocationId;
    public int paymentType;
    public double fareAmount;
    public double extra;
    public double mtaTax;
    public double tipAmount;
    public double tollsAmount;
    public double improvementSurcharge;
    public double totalAmount;
}
