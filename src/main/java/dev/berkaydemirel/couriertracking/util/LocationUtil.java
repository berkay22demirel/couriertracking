package dev.berkaydemirel.couriertracking.util;

import org.springframework.stereotype.Component;

@Component
public class LocationUtil {

    private static LocationUtil instance;

    private LocationUtil() {
    }

    public static synchronized LocationUtil getInstance() {
        if (instance == null) {
            instance = new LocationUtil();
        }
        return instance;
    }

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    public double calculateDistance(double startLat, double startLng, double endLat, double endLng) {

        double latDistance = Math.toRadians(startLat - endLat);
        double lngDistance = Math.toRadians(startLng - endLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(startLat))) *
                        (Math.cos(Math.toRadians(endLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return AVERAGE_RADIUS_OF_EARTH * c;
    }

}
