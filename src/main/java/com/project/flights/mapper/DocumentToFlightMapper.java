package com.project.flights.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import com.project.flights.model.Flight;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class DocumentToFlightMapper {
    public Flight mapDocumentToFlight(DocumentSnapshot document){
        Flight flight = new Flight();
        String flightNumber = document.getString("flightNumber");
        flight.setFlightNumber(flightNumber);

        String origin = document.getString("origin");
        flight.setOrigin(origin);

        String destination = document.getString("destination");
        flight.setDestination(destination);

        Date date = Objects.requireNonNull(document.getTimestamp("flightDeparture")).toDate();
        flight.setFlightDeparture(date);

        String airlineCompany = document.getString("airlineCompany");
        flight.setAirlineCompany(airlineCompany);

        String airport1 = document.getString("airport1");
        flight.setAirport1(airport1);

        String airport2 = document.getString("airport2");
        flight.setAirport2(airport2);

        Double adultPrice = document.getDouble("adultPrice");
        flight.setAdultPrice(adultPrice);

        return flight;
    }
}
