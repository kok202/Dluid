package org.kok202.deepblock.canvas.util;

import javafx.geometry.Point3D;

public class Math3D{
    public static Point3D getIntersectPoint(Point3D planeNormVector, double d, Point3D point1, Point3D point2){
        double numerator =
                planeNormVector.getX() * point1.getX() +
                        planeNormVector.getY() * point1.getY() +
                        planeNormVector.getZ() * point1.getZ() +
                        d;
        double denominator =
                planeNormVector.getX() * (point1.getX() - point2.getX()) +
                        planeNormVector.getY() * (point1.getY() - point2.getY()) +
                        planeNormVector.getZ() * (point1.getZ() - point2.getZ());
        if(denominator == 0)
            return null;
        double gradient = numerator / denominator;

        Point3D intersetedPoint = point1.add(point2.subtract(point1).multiply(gradient));
        return intersetedPoint;
    }

    public static double getDistance(Point3D start, Point3D end){
        return start.distance(end);
    }
}