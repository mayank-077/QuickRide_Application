package com.SPRINGBOOT.project.QuickRide.QuickRideApp.utils;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

//To create the point on the geometry
//convert the coordinates into the point
// and defining the points will be of earth coordinates
public class GeometryUtil {

    // this can be used any places so that's why satatic
    //utility method

    public static Point createPoint(PointDto pointDto) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);// initializing the geometry class and telling the geometry is of earth
        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0], // creating the corodinates
                pointDto.getCoordinates()[1]
        ); // passing the (longitude, latitude)
        return geometryFactory.createPoint(coordinate); // passing the coordinates and calling the fucntion createopoint to create and then returning the created points
    }
}