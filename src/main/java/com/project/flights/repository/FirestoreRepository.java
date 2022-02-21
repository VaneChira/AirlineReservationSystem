package com.project.flights.repository;

import com.project.flights.model.Flight;

import java.util.List;
import java.util.Map;

public interface FirestoreRepository {
    Flight addFlight(Flight flight);

    void removeFlightById(String flightNumber);

    void updateFlight(String flightNumber, Map flightDetails);

    List<Flight> getAll();

    Flight getFlightById(String flightNumber);

}
