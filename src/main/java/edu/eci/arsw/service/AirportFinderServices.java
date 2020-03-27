package edu.eci.arsw.service;

import edu.eci.arsw.model.Airport;

import java.util.List;

public interface AirportFinderServices {
    List<Airport> findAirportsByName(String name);
}
