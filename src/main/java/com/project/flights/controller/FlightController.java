package com.project.flights.controller;

import com.project.flights.model.Flight;
import com.project.flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/flight", produces = APPLICATION_JSON_VALUE)
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/addFlight")
    public Flight addFlight(@RequestBody Flight flight){
        return flightService.addFlight(flight);
    }

    @GetMapping("/getAllFlights")
    public List<Flight> getAll(){
        return flightService.getAllFlights();
    }

    @GetMapping("/getFlightById/{flightNumber}")
    public Flight getFlightById(@PathVariable String flightNumber){
        return flightService.getFlightById(flightNumber);
    }

    @DeleteMapping("/delete-flight/{flightNumber}")
    public void delete(@PathVariable String flightNumber){
        flightService.deleteFlight(flightNumber);
    }

    @PutMapping("/update-flight/{flightNumber}")
    public void updateFlight(@PathVariable String flightNumber, @RequestBody Flight flightDetails){
        this.flightService.updateFlight(flightNumber, flightDetails);
    }
}
