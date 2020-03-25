package edu.eci.arsw.cache.impl;

import edu.eci.arsw.cache.AirportsFinderCache;
import edu.eci.arsw.model.Airport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AirportsFinderCacheImpl implements AirportsFinderCache {

    private ConcurrentHashMap<String,List<Airport>> concurrentHashMapAirports;
    private ConcurrentHashMap<String,Long> concurrentHashMapCacheTime;

    public AirportsFinderCacheImpl(){
        concurrentHashMapAirports = new ConcurrentHashMap<>();
        concurrentHashMapCacheTime = new ConcurrentHashMap<>();
    }

    @Override
    public List<Airport> getAirportsByName(String name) {
        return concurrentHashMapAirports.get(name);
    }

    @Override
    public void saveAirports(String name, List<Airport> airports) {
        concurrentHashMapAirports.put(name,airports);
        concurrentHashMapCacheTime.put(name,System.nanoTime());
    }

    @Override
    public Long getTimeInCache(String name) {
        return concurrentHashMapCacheTime.get(name);
    }
}
