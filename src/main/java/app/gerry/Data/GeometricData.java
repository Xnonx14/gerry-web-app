package app.gerry.Data;

import org.locationtech.jts.geom.Geometry;

import java.awt.*;
import java.util.Set;

public class GeometricData {
    private double area;
    private double perimeter;
    private Geometry convexHull;
    private Geometry shape;

    public GeometricData(double area, double perimeter, Geometry convexHull, Geometry shape){
        this.area = area;
        this.perimeter = perimeter;
        this.convexHull = convexHull;
        this.shape = shape;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimenter) {
        this.perimeter = perimenter;
    }

    public Geometry getConvexHull() {
        return convexHull;
    }

    public void setConvexHull(Geometry convexHull) {
        this.convexHull = convexHull;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Geometry getShape() {
        return shape;
    }

    public void setShape(Geometry shape) {
        this.shape = shape;
    }
}
