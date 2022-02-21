package com.project.flights.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Seat {
    private int seatNumber;
    private Class seatType;
    private Status seatStatus = Status.AVAILABLE;
//    private double price;

    public Seat(int seatNumber, Class seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    private enum Status{
        AVAILABLE, RESERVED;
    }
}
