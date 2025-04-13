package com.SPRINGBOOT.project.QuickRide.QuickRideApp.cofigs;

// mapping process b/w the DTO and the entities and viceversa
// it allow the easy conversion form one object type to another ex: UserDTO is mapped to user
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.PointDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //and we have made a mapper for only poindto to point only
        //bcz at ride req controller we taking the pointdto but we in ridereq entity it is point
        //so in mapping we have make a mapper

        //converting the one type to another(point dto to points created by Geometry
        //point dto to point

        //setconverter is about how to convert
        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> { // lambda function
            PointDto pointDto = context.getSource(); // source point
            return GeometryUtil.createPoint(pointDto); // returning the created points
        });

        //convert the point to point DTO
        mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {
            Point point = context.getSource();
            double coordinates[] = {
                    point.getX(), //longitude
                    point.getY()  // latitude
            };
            return new PointDto(coordinates);
        });

        return mapper;
    }
}