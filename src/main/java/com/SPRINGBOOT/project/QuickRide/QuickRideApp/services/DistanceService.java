package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;
//Calculating the distance
// Road distance(problem dist b/w coordinates = displacement)
// so we will use the googlemap API (paid)
//free = OSRM(open Source) (give the dist between the two points)
// it will give the array of all the distances and sort it

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.locationtech.jts.geom.Point;

public interface DistanceService {

    double calculateDistance(Point src, Point dest);

}
