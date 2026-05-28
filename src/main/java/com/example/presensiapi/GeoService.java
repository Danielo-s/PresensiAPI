package com.example.presensiapi;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

@Service
public class GeoService {
    private final GeometryFactory gf = new GeometryFactory();
    private final Polygon polygon;

    public GeoService() {
        // Sesuaikan titik koordinat ini dengan area kampus/kantor Anda
        Coordinate[] coords = new Coordinate[]{
            new Coordinate(107.147450, -6.348360),
            new Coordinate(107.148073, -6.347481),
            new Coordinate(107.151397, -6.349691),
            new Coordinate(107.150883, -6.350577),
            new Coordinate(107.147450, -6.348360) // Titik penutup (sama dengan titik awal)
        };
        this.polygon = gf.createPolygon(coords);
    }

    public boolean isInside(double lat, double lng) {
        // JTS menggunakan format (lng, lat) atau (x, y)
        Point point = gf.createPoint(new Coordinate(lng, lat));
        return polygon.covers(point);
    }
}