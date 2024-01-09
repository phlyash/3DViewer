package com.cgvsu.render_engine.Rasterizers;

import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Points.PolygonPoint;
import com.cgvsu.render_engine.Rasterizer;
import javafx.scene.paint.Color;

public class BaseRasterizer {
    // draws line of triangle considering Z coordinate
    // from p1 to p2, p3 is for calculating Z
    // todo try to use easier rasterizer cuz lines are really thick
    protected static void drawLine(Point3D p1, Point3D p2, Point3D p3, Color color) {
        boolean steep = Math.abs(p2.getY() - p1.getY()) > Math.abs(p2.getX() - p1.getX());
        if (steep) { // swap
            p1 = new Point3D(p1.getY(), p1.getX(), p1.getZ());
            p2 = new Point3D(p2.getY(), p2.getX(), p2.getZ());
        }
        if (p1.getX() > p2.getX()) {
            Point3D swap = p1;
            p1 = p2;
            p2 = swap;
        }

        Rasterizer rasterizer = Rasterizer.getRasterizer();

        if (!steep) {
            rasterizer.setColor(p1, p2, p3, p1.getX(), p1.getY(), color);
            rasterizer.setColor(p1, p2, p3, p2.getX(), p2.getY(), color);
        } else {
            rasterizer.setColor(p1, p2, p3, p1.getY(), p1.getX(), color);
            rasterizer.setColor(p1, p2, p3, p2.getY(), p2.getX(), color);
        }

        float dx = p2.getX() - p1.getX();
        float dy = p2.getY() - p1.getY();
        float gradientY = dy / dx;
        if (dx == 0)
            gradientY = 1;

        float y = p1.getY();

        if (!steep)
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                rasterizer.setColor(p1, p2, p3, x, (int) y, color);
                rasterizer.setColor(p1, p2, p3, x, (int) y - 1, color);
                y += gradientY;
            }
        else
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                rasterizer.setColor(p1, p2, p3, (int) y, x, color);
                rasterizer.setColor(p1, p2, p3, (int) y - 1, x, color);
                y += gradientY;
            }
    }

//    protected static void drawLine(Point3D p1, Point3D p2, Point3D p3, Color color) {
//        if(p2.getX() < p1.getX()) {
//            Point3D swap = p1;
//            p1 = p2;
//            p2 = swap;
//        }
//        int deltaX = Math.abs(p1.getX() - p2.getX());
//        int deltaY = Math.abs(p2.getY() - p1.getY());
//        int y = p1.getY();
//        int dirY = p2.getY() - y;
//
//        int error = 0;
//        int deltaError = deltaY + 1;
//
//        if (dirY > 0) dirY = 1;
//        if (dirY < 0) dirY = -1;
//        Rasterizer rasterizer = Rasterizer.getRasterizer();
//
//        for(int x = p1.getX(); x <= p2.getX(); x++) {
//            rasterizer.setColor(p1, p2, p3, x, y, color);
//            error += deltaError;
//
//            if(error >= (deltaX + 1)) {
//                y += dirY;
//                error -= deltaX - 1;
//            }
//        }
//    }

    protected static void drawParallel(PolygonPoint p1, PolygonPoint p2, PolygonPoint p3, int x1, int x2, int y) {
        int xStart = Math.min(x1, x2);
        int xEnd = x1 + x2 - xStart;
        Rasterizer rasterizer = Rasterizer.getRasterizer();

        for(; xStart <= xEnd; xStart++) {
            rasterizer.setColorWithoutEpsilon(p1, p2, p3, xStart, y);
        }
    }
}
