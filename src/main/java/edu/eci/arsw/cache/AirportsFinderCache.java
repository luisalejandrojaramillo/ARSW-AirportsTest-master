package edu.eci.arsw.cache;

import edu.eci.arsw.model.Airport;

import java.util.List;

public interface AirportsFinderCache {

    List<Airport> getAirportsByName(String name);
    void saveAirports(String name,List<Airport> airports);
    Long getTimeInCache(String name);

}
