package com.project.flights.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDetails {
    private String reservationNo;
    private User user;
    private Flight flight;
//    private int numberOfPassengers;
//    private Seat seat;


    public ReservationDetails(String reservationNo, User user, Flight flight) {
        this.reservationNo = reservationNo;
        this.user = user;
        this.flight = flight;
    }
}
