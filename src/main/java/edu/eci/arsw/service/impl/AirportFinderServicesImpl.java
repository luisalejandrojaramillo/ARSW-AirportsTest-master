package edu.eci.arsw.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.eci.arsw.cache.AirportsFinderCache;
import edu.eci.arsw.model.Airport;
import edu.eci.arsw.service.AirportFinderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AirportFinderServicesImpl implements AirportFinderServices {

    private String url;
    private String headerHost;
    private String headerHostValue;
    private String headerKey;
    private String headerKeyValue;
    private Gson gson;

    @Autowired
    private AirportsFinderCache airportsFinderCache;

    public AirportFinderServicesImpl(){
        url = "https://cometari-airportsfinder-v1.p.rapidapi.com/api/";
        headerHost = "x-rapidapi-host";
        headerHostValue = "cometari-airportsfinder-v1.p.rapidapi.com";
        headerKey = "x-rapidapi-key";
        headerKeyValue = "9cd3954848msh0c2f0da83391e1ap176616jsn57d0f92a29ed";
        gson = new GsonBuilder().create();
    }

    @Override
    public List<Airport> findAirportsByName(String name) {
        String encodedUrlName = null;
        try {
            encodedUrlName = URLEncoder.encode(name,java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder apiUrl = new StringBuilder();
        apiUrl.append(url);
        apiUrl.append("airports/by-text?text="+encodedUrlName);

        HttpResponse<String> apiResponse = null;
        try{
            apiResponse = Unirest.get(apiUrl.toString())
                    .header(headerHost,headerHostValue)
                    .header(headerKey,headerKeyValue)
                    .asString();
        }catch (UnirestException e){
            e.printStackTrace();
        }

        List<Airport> airports = null;
        if (airportsFinderCache.getAirportsByName(name) == null){
            airports = gson.fromJson(apiResponse.getBody(),new TypeToken<List<Airport>>(){}.getType());
            airportsFinderCache.saveAirports(name,airports);
        }
        else{
            Long timeInCache = airportsFinderCache.getTimeInCache(name);
            Long elapsedTime = System.nanoTime() - timeInCache;
            Long time = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            if (time > 300){
                airports = gson.fromJson(apiResponse.getBody(),new TypeToken<List<Airport>>(){}.getType());
                airportsFinderCache.saveAirports(name,airports);
            }
            else{
                airports = airportsFinderCache.getAirportsByName(name);
            }
        }
        return airports;
    }
}
