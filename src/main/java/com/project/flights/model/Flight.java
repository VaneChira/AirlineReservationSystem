package com.project.flights.model;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private Date flightDeparture;
    private String airlineCompany;
    private String airport1;
    private String airport2;
    private double adultPrice; // child = adult / 2;

//    private Set<Seat> seats;


    public Flight(String flightNumber, String origin, String destination, Date flightDeparture, String airlineCompany, String airport1, String airport2, double adultPrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.flightDeparture = flightDeparture;
        this.airlineCompany = airlineCompany;
        this.airport1 = airport1;
        this.airport2 = airport2;
        this.adultPrice = adultPrice;
    }
}
