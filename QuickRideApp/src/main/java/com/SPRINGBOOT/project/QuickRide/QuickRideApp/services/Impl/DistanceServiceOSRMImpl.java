package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

//Rest client Api - OSRM API for the distance calculation

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "https://router.project-osrm.org/route/v1/driving/"; // calling the api

    @Override
    public double calculateDistance(Point src, Point dest) {
        try {
            String uri = src.getX()+","+src.getY()+";"+dest.getX()+","+dest.getY(); // appending the loation coordinate to the url
            OSRMResponseDto responseDto = RestClient.builder() //rest client is used to call 3rd party api
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri) //passing the coordinate
                    .retrieve() // this method get the data from above calling and converted in below line by body method
                    .body(OSRMResponseDto.class); // to map the objects we only want the top routes

            return responseDto.getRoutes().get(0).getDistance() / 1000.0; // dividing by converts into the KM
        } catch (Exception e) {
            throw new RuntimeException("Error getting data from OSRM "+e.getMessage());
        }
    }
}

@Data
class OSRMResponseDto {
    private List<OSRMRoute> routes; //list of the routes we get
}

@Data //includes all the getters and the setters
class OSRMRoute {
    private Double distance; //we want the routes on the basis of distance
}
