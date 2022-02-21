package com.project.flights.service;

import com.project.flights.model.Flight;
import com.project.flights.repository.FirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class FlightService {
    private final FirestoreRepository firestoreRepository;

    @Autowired
    public FlightService(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    public Flight addFlight(Flight flight){
        return firestoreRepository.addFlight(flight);
    }

    public List<Flight> getAllFlights(){
        return firestoreRepository.getAll();
    }

    public void deleteFlight(String flightNumber){
        firestoreRepository.removeFlightById(flightNumber);
    }

    public void updateFlight(String flightNumber, Flight flightDetails){
        HashMap<String, Object> updateDetails = new HashMap<>();
        updateDetails.put("flightNumber", flightDetails.getFlightNumber());
        updateDetails.put("origin", flightDetails.getOrigin());
        updateDetails.put("destination", flightDetails.getDestination());
        updateDetails.put("flightDeparture", flightDetails.getFlightDeparture());
        updateDetails.put("airlineCompany", flightDetails.getAirlineCompany());
        updateDetails.put("airport1", flightDetails.getAirport1());
        updateDetails.put("airport2", flightDetails.getAirport2());
        updateDetails.put("adultPrice", flightDetails.getAdultPrice());
        this.firestoreRepository.updateFlight(flightNumber, updateDetails);
    }

    public Flight getFlightById(String flightNumber){
        return firestoreRepository.getFlightById(flightNumber);
    }
}
